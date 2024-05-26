package ru.practicum.android.diploma.ui.filtration.industry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.domain.api.industry.IndustryInteractor
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.util.SearchResultData

class IndustryViewModel(private val interactor: IndustryInteractor) : ViewModel() {
    private val industriesList: MutableList<Industry> = mutableListOf()
    private var isClickAllowed = true

    private val _stateIndustry = MutableLiveData<IndustryState>()
    val stateIndustry: LiveData<IndustryState> get() = _stateIndustry

    private fun searchIndustries() {
        _stateIndustry.postValue(IndustryState.Loading)
        viewModelScope.launch {
            interactor.getIndustries().collect { result ->
                industriesList.clear()
                processResult(result)
            }
        }
    }

    private fun processResult(result: SearchResultData<List<Industry>>) {
        when (result) {
            is SearchResultData.Data -> {
                if (result.value != null) {
                    industriesList.clear()
                    industriesList.addAll(result.value)
                    industriesList.sortBy { it.name }
                } else {
                    _stateIndustry.postValue(IndustryState.NotFound)
                }
            }

            is SearchResultData.ServerError -> {
                _stateIndustry.postValue(IndustryState.ServerError(R.string.search_server_error))
            }

            is SearchResultData.NoConnection -> {
                _stateIndustry.postValue(IndustryState.NoConnection(R.string.search_no_connection))
            }
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
        private const val CLICK_DEBOUNCE_DELAY_MILLIS = 1000L
    }
}
