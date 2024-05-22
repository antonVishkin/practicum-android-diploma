package ru.practicum.android.diploma.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.dictionary.DictionaryInteractor
import ru.practicum.android.diploma.domain.api.search.SearchInteractor
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(
    private val searchInteractor: SearchInteractor,
    private val dictionaryInteractor: DictionaryInteractor
) : ViewModel() {
    private var lastSearchQueryText: String? = null
    private var isNextPageLoading = true
    private var currPage: Int? = null
    private var maxPage: Int? = null
    private val vacancySearchDebounce = debounce<String>(SEARCH_DEBOUNCE_DELAY_MILLIS, viewModelScope, true) {
        search(it, hashMapOf())
    }

    private val _stateSearch = MutableLiveData<SearchState>(SearchState.Default)
    val stateSearch: LiveData<SearchState> get() = _stateSearch

    fun search(request: String, options: HashMap<String, String>) {
        if (request.isNullOrEmpty()) {
            renderState(SearchState.Default)
        } else {
            renderState(SearchState.Loading)
            searchVacancies(request, options)
        }
    }

    private fun renderState(state: SearchState) {
        _stateSearch.value = state
    }

    fun setStateDefault() {
        _stateSearch.postValue(SearchState.Default)
    }

    fun onLastItemReached() {
        if (isNextPageLoading) {
            isNextPageLoading = false
            if (currPage!! < maxPage!!) {
                renderState(SearchState.NewPageLoading)
                currPage = currPage!! + 1
                val t = hashMapOf<String, String>()
                t.put("page", "$currPage")
                searchVacancies(lastSearchQueryText ?: "", t)
            }
        }
    }

    private fun searchVacancies(request: String, options: HashMap<String, String>) {
        options["text"] = request
        viewModelScope.launch {
            val currencyDictionary = dictionaryInteractor.getCurrencyDictionary()
            searchInteractor.searchVacancies(options).collect { result ->
                result.onSuccess {
                    currPage = it.currPage
                    maxPage = it.fromPages
                    Log.v("SEARCH", "page $currPage list ${it.vacancyList}")
                    when {
                        (it.currPage == 0 && it.vacancyList.isEmpty()) -> renderState(SearchState.Empty)
                        (it.currPage != 0 && it.vacancyList.isEmpty()) -> renderState(SearchState.LastPage)
                        else -> {
                            renderState(
                                SearchState.Content(
                                    vacancyPage = it,
                                    currencyDictionary = currencyDictionary
                                )
                            )
                        }
                    }
                }
                result.onFailure {
                    if (currPage != 0) {
                        renderState(SearchState.NextPageError)
                    } else {
                        renderState(SearchState.ServerError(it.message ?: ""))
                    }
                }
                isNextPageLoading = true
            }
        }
    }

    fun searchDebounce(changedText: String) {
        if (lastSearchQueryText == changedText) return
        this.lastSearchQueryText = changedText
        vacancySearchDebounce(changedText)
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
    }
}
