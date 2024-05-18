package ru.practicum.android.diploma.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.search.SearchInteractor

class SearchViewModel(
    private val searchInteractor: SearchInteractor
) : ViewModel() {
    private var searchJob: Job? = null
    private var isClickAllowed = true
    private var lastSearchQueryText: String? = null

    private val _stateSearch = MutableLiveData<SearchState>(SearchState.Default)
    val stateSearch: LiveData<SearchState> get() = _stateSearch

    fun search(request:String){
        val options = HashMap<String,String>()
        options.put("text",request)
        viewModelScope.launch {
            searchInteractor.searchVacancies(options).collect {result ->
                result.onSuccess {
                    Log.v("VACANCY", "succes" + it.toString())
                }
                result.onFailure {
                    Log.v("VACANCY", "failure" + it.toString())
                }
            }
        }
    }
}
