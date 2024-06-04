package ru.practicum.android.diploma.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.dictionary.DictionaryInteractor
import ru.practicum.android.diploma.domain.api.filtration.FiltrationInteractor
import ru.practicum.android.diploma.domain.api.search.SearchInteractor
import ru.practicum.android.diploma.domain.models.Currency
import ru.practicum.android.diploma.domain.models.Filtration
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyPage
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(
    private val searchInteractor: SearchInteractor,
    private val dictionaryInteractor: DictionaryInteractor,
    private val filtrationInteractor: FiltrationInteractor,
) : ViewModel() {
    private var lastSearchQueryText: String? = null
    private var isNextPageLoading = true
    private var currPage: Int? = null
    private var maxPage: Int? = null
    private val vacancySearchDebounce =
        debounce<Pair<String, HashMap<String, String>>>(SEARCH_DEBOUNCE_DELAY_MILLIS, viewModelScope, true) {
            searchVacancies(it.first, it.second)
        }

    private val _stateSearch = MutableLiveData<SearchState>(SearchState.Default)
    val stateSearch: LiveData<SearchState> get() = _stateSearch
    private val _filtration = MutableLiveData<Filtration?>(null)
    val filtration: LiveData<Filtration?> get() = _filtration
    private val _newPageLoading = MutableLiveData(false)
    val newPageLoading: LiveData<Boolean> get() = _newPageLoading

    private fun search(request: String, options: HashMap<String, String>) {
        if (request.isNullOrEmpty()) {
            renderState(SearchState.Default)
        } else {
            renderState(SearchState.Loading)
            currPage = null
            vacancySearchDebounce(Pair(request, options))
        }
    }

    private fun renderState(state: SearchState) {
        _stateSearch.postValue(state)
    }

    fun setStateDefault() {
        _stateSearch.postValue(SearchState.Default)
    }

    fun onLastItemReached() {
        if (isNextPageLoading) {
            isNextPageLoading = false
            if (currPage!! < maxPage!!) {
                _newPageLoading.postValue(true)
                currPage = currPage!! + 1
                val t = hashMapOf<String, String>()
                t.put("page", "$currPage")
                vacancySearchDebounce(Pair(lastSearchQueryText ?: "", t))
            }
        }
    }

    fun updateFiltration() {
        viewModelScope.launch {
            val newFiltration = filtrationInteractor.getFiltration()
            if (filtration.value != newFiltration) {
                _filtration.postValue(newFiltration)
                if (lastSearchQueryText != null) {
                    search(lastSearchQueryText!!, hashMapOf())
                }
            }
        }
    }

    private fun searchVacancies(request: String, options: HashMap<String, String>) {
        viewModelScope.launch {
            _filtration.postValue(filtrationInteractor.getFiltration())
            if (filtration.value != null) {
                options.putAll(convertFiltrationToOptions(filtration.value!!))
            }
            options["text"] = request
            val currencyDictionary = dictionaryInteractor.getCurrencyDictionary()
            searchInteractor.searchVacancies(options).collect { result ->
                _newPageLoading.postValue(false)
                result.onSuccess {
                    val oldPage = if (stateSearch.value is SearchState.Content) {
                        (stateSearch.value as SearchState.Content).vacancyPage
                    } else {
                        null
                    }
                    val resulList = mutableListOf<Vacancy>()
                    resulList.addAll(oldPage?.vacancyList ?: listOf())
                    resulList.addAll(it.vacancyList)
                    onSearchSuccess(VacancyPage(resulList, it.currPage, it.fromPages, it.found), currencyDictionary)
                }
                result.onFailure {
                    Log.v("SEARCH", "page $currPage error ${it.message}")
                    onSearchFailure(it.message)
                }
                isNextPageLoading = true
            }
        }
    }

    private fun onSearchFailure(message: String?) {
        if (message != "-1") {
            if (currPage != 0 && currPage != null) {
                renderState(SearchState.NextPageError)
            } else {
                renderState(SearchState.ServerError(message ?: ""))
            }
        } else {
            if (currPage != 0 && currPage != null) {
                renderState(SearchState.NextPageError)
            } else {
                renderState(SearchState.NoConnection)
            }
        }

    }

    private fun onSearchSuccess(page: VacancyPage, currencyDictionary: Map<String, Currency>) {
        currPage = page.currPage
        maxPage = page.fromPages
        Log.v("SEARCH", "page $currPage list ${page.vacancyList}")
        when {
            page.currPage == 0 && page.vacancyList.isEmpty() -> renderState(SearchState.Empty)
            page.currPage != 0 && page.vacancyList.isEmpty() -> renderState(SearchState.LastPage)
            else -> {
                renderState(
                    SearchState.Content(
                        vacancyPage = page,
                        currencyDictionary = currencyDictionary
                    )
                )
            }
        }
    }

    fun searchDebounce(changedText: String) {
        if (lastSearchQueryText == changedText) return
        this.lastSearchQueryText = changedText
        search(changedText, hashMapOf())
    }

    private fun convertFiltrationToOptions(filtrationParams: Filtration): HashMap<String, String> {
        val options = hashMapOf<String, String>()
        if (filtrationParams.area != null) {
            options["area"] = filtrationParams.area.id
            if (filtrationParams.area.regions.isNotEmpty()) {
                options["area"] = filtrationParams.area.regions[0].id
            }
        }
        if (filtrationParams.industry != null) {
            options["industry"] = filtrationParams.industry.id
        }
        if (!filtrationParams.salary.isNullOrEmpty()) {
            options["salary"] = filtrationParams.salary
        }
        if (filtrationParams.onlyWithSalary) {
            options["only_with_salary"] = "true"
        }
        return options
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
    }
}
