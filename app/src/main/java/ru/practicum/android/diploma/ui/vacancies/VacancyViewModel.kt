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

    private val _stateLiveData = MutableLiveData<VacancyState>()
    val stateLiveData:LiveData<VacancyState> get() = _stateLiveData

    fun fetchVacancyDetails(vacancyId: String) {
        renderState(VacancyState.Loading)
        viewModelScope.launch {
            vacancyInteractor.getVacancyDetails(vacancyId).collect { result ->
                result.onSuccess { vacancyDetails ->
                    renderState(
                        VacancyState.Content(vacancyDetails)
                    )
                }.onFailure {
                    renderState(
                        VacancyState.Error
                    )
                }
            }
        }
    }

    private fun renderState(state: VacancyState) {
        _stateLiveData.value = state
    }
}
