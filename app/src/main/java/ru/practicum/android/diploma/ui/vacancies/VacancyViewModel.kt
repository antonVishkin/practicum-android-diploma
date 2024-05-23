package ru.practicum.android.diploma.ui.vacancies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.api.dictionary.DictionaryInteractor

class VacancyViewModel(
    private val vacancyInteractor: VacancyDetailsInteractor, private val dictionaryInteractor: DictionaryInteractor,
) : ViewModel() {

    private val _stateLiveData = MutableLiveData<VacancyState>()
    val stateLiveData: LiveData<VacancyState> get() = _stateLiveData

    fun fetchVacancyDetails(vacancyId: String) {
        renderState(VacancyState.Loading)
        viewModelScope.launch {
            val currencyDictionary = dictionaryInteractor.getCurrencyDictionary()
            vacancyInteractor.getVacancyDetails(vacancyId).collect { result ->
                result.onSuccess { vacancyDetails ->
                    val currSymbol = currencyDictionary[vacancyDetails?.salary?.currency]?.abbr ?: ""
                    renderState(
                        VacancyState.Content(vacancyDetails, currSymbol)
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
