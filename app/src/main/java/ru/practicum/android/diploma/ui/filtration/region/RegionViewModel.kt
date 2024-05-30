package ru.practicum.android.diploma.ui.filtration.region

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.region.RegionInteractor
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.util.SearchResultData

class RegionViewModel(private val regionInteractor: RegionInteractor) : ViewModel() {

    private val _regions = MutableLiveData<List<Region>>()
    val regions: LiveData<List<Region>> = _regions

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchRegions(countryId: String) {
        viewModelScope.launch {
            regionInteractor.getRegions(countryId).collect { result: SearchResultData<List<Region>> ->
                when (result) {
                    is SearchResultData.Data -> {
                        _regions.value = result.value!!
                    }

                    is SearchResultData.Error -> {
                        _error.value = "Server Error: ${result.message}"
                    }

                    is SearchResultData.NoConnection -> {
                        _error.value = "No Connection: ${result.message}"
                    }

                    is SearchResultData.NotFound -> TODO()
                }
            }
        }
    }
}

