package ru.practicum.android.diploma.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.dictionary.DictionaryInteractor
import ru.practicum.android.diploma.domain.api.search.SearchInteractor

class SearchViewModel(
    private val searchInteractor: SearchInteractor,
    private val dictionaryInteractor: DictionaryInteractor
) : ViewModel() {
    private var searchJob: Job? = null
    private var lastSearchQueryText: String? = null
    private var isNextPageLoading = true
    private var currPage: Int? = null
    private var maxPage: Int? = null

    private val _stateSearch = MutableLiveData<SearchState>(SearchState.Default)
    val stateSearch: LiveData<SearchState> get() = _stateSearch

    fun search(request: String, options: HashMap<String, String>) {
        renderState(SearchState.Loading)
        searchVacancies(request, options)
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
                searchVacancies("test", t)
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
                    renderState(
                        SearchState.Content(
                            vacancyPage = it,
                            currencyDictionary = currencyDictionary
                        )
                    )
                }
                result.onFailure {
                    Log.v("VACANCY", "failure" + it.toString())
                }
                isNextPageLoading = true
            }
        }
    }

    fun searchDebounce(changedText: String) {
        if (lastSearchQueryText == changedText) return
        this.lastSearchQueryText = changedText
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY_MILLIS)
            search(changedText, hashMapOf())
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
    }
}
