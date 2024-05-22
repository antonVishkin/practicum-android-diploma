package ru.practicum.android.diploma.ui.vacancies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.models.VacancyDetails

class VacancyViewModel(
    private val vacancyInteractor: VacancyDetailsInteractor
) : ViewModel() {

    private val _vacancyDetails = MutableLiveData<VacancyDetails>()
    val vacancyDetails: LiveData<VacancyDetails>
        get() = _vacancyDetails

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun fetchVacancyDetails(vacancyId: String) {
        viewModelScope.launch {
            _loading.value = true
            vacancyInteractor.getVacancyDetails(vacancyId).collect { result ->
                result.onSuccess { vacancyDetails ->
                    _vacancyDetails.value = vacancyDetails
                    _loading.value = false
                }.onFailure { error ->
                    _error.value = error.message
                    _loading.value = false
                }
            }
        }
    }
}
