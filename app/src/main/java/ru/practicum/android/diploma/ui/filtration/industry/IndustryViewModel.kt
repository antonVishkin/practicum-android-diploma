package ru.practicum.android.diploma.ui.filtration.industry

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.api.industry.IndustryInteractor
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.ui.filtration.FiltrationState
import ru.practicum.android.diploma.util.SearchResultData

class IndustryViewModel(private val interactor: IndustryInteractor) : ViewModel() {
    private val industriesList: MutableList<Industry> = mutableListOf()
    private var selectedIndustry: Industry? = null
    private var lastSearchQueryText: String? = null
    private var isClickAllowed = true

    private val _stateIndustry = MutableLiveData<IndustryState>()
    val stateIndustry: LiveData<IndustryState> get() = _stateIndustry

    fun searchIndustries() {
        _stateIndustry.postValue(IndustryState.Loading)
        viewModelScope.launch {
            interactor.getIndustries().collect() { result ->
                industriesList?.clear()
                processResult(result)
            }
        }
    }

    private fun processResult(result: SearchResultData<List<Industry>>) {
        when (result) {
            is SearchResultData.Data -> {
                if (result.value != null) {
                    Log.v("INDUSTRY", "1 ${result.value}")
                    industriesList.clear()
                    industriesList.addAll(result.value)
                    industriesList.sortBy { it.name }
                    Log.v("INDUSTRY", "2 $industriesList")
                    _stateIndustry.postValue(IndustryState.Content(industriesList!!))
                } else {
                    _stateIndustry.postValue(IndustryState.NotFound)
                }
            }

            is SearchResultData.Error -> {
                _stateIndustry.postValue(IndustryState.ServerError(R.string.search_server_error))
            }

            is SearchResultData.NoConnection -> {
                _stateIndustry.postValue(IndustryState.NoConnection(R.string.search_no_connection))
            }

            is SearchResultData.NotFound -> {
                _stateIndustry.postValue(IndustryState.NotFound)
            }
        }
    }

    fun search(s: String) {
        _stateIndustry.postValue(IndustryState.Loading)
        viewModelScope.launch {
            val filteredIndustry = industriesList?.filter { it.name.contains(s, ignoreCase = true) }
            if (filteredIndustry.isNullOrEmpty()) {
                _stateIndustry.postValue(IndustryState.NotFound)
            } else {
                _stateIndustry.postValue(IndustryState.Content(filteredIndustry))
            }
        }
    }

    fun saveSelectIndustry() {
        _stateIndustry.postValue(IndustryState.Selected)
    }

    fun searchDebounce(changedText: String) {
        if (lastSearchQueryText == changedText) return
        this.lastSearchQueryText = changedText
        viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY_MILLIS)
            search(changedText)
        }
    }

    fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            viewModelScope.launch {
                delay(CLICK_DEBOUNCE_DELAY_MILLIS)
                isClickAllowed = true
            }
        }
        return current
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY_MILLIS = 2000L
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
    }
}
