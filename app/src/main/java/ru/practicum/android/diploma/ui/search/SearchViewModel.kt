package ru.practicum.android.diploma.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import ru.practicum.android.diploma.domain.search.SearchInteractor

class SearchViewModel(val interactor: SearchInteractor) : ViewModel() {
    private var searchJob: Job? = null
    private var isClickAllowed = true
    private var lastSearchQueryText: String? = null

    private val _stateSearch = MutableLiveData<SearchState>(SearchState.Default)
    val stateSearch: LiveData<SearchState> get() = _stateSearch
}
