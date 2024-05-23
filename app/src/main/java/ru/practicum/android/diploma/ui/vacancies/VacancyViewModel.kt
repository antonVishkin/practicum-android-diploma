package ru.practicum.android.diploma.ui.vacancies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.api.dictionary.DictionaryInteractor
import ru.practicum.android.diploma.domain.api.favorites.FavoritesInteractor
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyViewModel(
    private val vacancyInteractor: VacancyDetailsInteractor,
    private val dictionaryInteractor: DictionaryInteractor,
    private val favoritesInteractor: FavoritesInteractor,
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
                    val isFavorite = favoritesInteractor.isVacancyFavorite(vacancyDetails.id)
                    renderState(
                        VacancyState.Content(vacancyDetails, currSymbol, isFavorite)
                    )
                }.onFailure {
                    renderState(
                        VacancyState.Error
                    )
                }
            }
        }
    }

    fun addToFavorite() {
        val state = stateLiveData.value as VacancyState.Content
        val vacancyDetails = state.vacancyDetails
        val vacancy = Vacancy(
            id = vacancyDetails.id,
            name = vacancyDetails.name,
            salary = vacancyDetails.salary,
            city = vacancyDetails.areaName,
            employerName = vacancyDetails.employerName ?: "",
            urlImage = vacancyDetails.logoUrl
        )
        viewModelScope.launch {
            if (state.isFavorite) {
                favoritesInteractor.removeVacancyFromFavorites(vacancy)
                favoritesInteractor.removeVacancyDetails(vacancyDetails.id)
            } else {
                favoritesInteractor.addVacancyDetails(vacancyDetails)
                favoritesInteractor.addVacancyToFavorites(vacancy)
            }
            renderState(
                VacancyState.Content(
                    vacancyDetails = vacancyDetails,
                    currencySymbol = state.currencySymbol,
                    isFavorite = !state.isFavorite
                )
            )
        }
    }

    private fun renderState(state: VacancyState) {
        _stateLiveData.value = state
    }
}
