package ru.practicum.android.diploma.ui.filtration.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.country.CountryInteractor
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.util.SearchResultData

class CountryViewModel(private val countryInteractor: CountryInteractor) : ViewModel() {

    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> = _countries

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchCountries() {
        viewModelScope.launch {
            countryInteractor.getCountries().collect { result: SearchResultData<List<Country>> ->
                when (result) {
                    is SearchResultData.Data -> {
                        _countries.value = result.value!!
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
