package ru.practicum.android.diploma.ui.filtration.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.filtration.country.CountryInteractor
import ru.practicum.android.diploma.domain.api.filtration.region.RegionsInteractor
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.util.SearchResultData

class RegionsViewModel(private val regionsInteractor: RegionsInteractor) : ViewModel() {

    private val _regions = MutableLiveData<List<Country>>()
    val regions: LiveData<List<Country>> = _regions

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchRegions() {
        viewModelScope.launch {
            regionsInteractor.getRegions().collect { result: SearchResultData<List<Country>> ->
                when (result) {
                    is SearchResultData.Data -> {
                        _regions.value = result.value!!
                    }
                    is SearchResultData.ServerError -> {
                        _error.value = "Server Error: ${result.message}"
                    }
                    is SearchResultData.NoConnection -> {
                        _error.value = "No Connection: ${result.message}"
                    }
                }
            }
        }
    }
}



