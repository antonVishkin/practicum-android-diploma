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
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.sharing.SharingInteractor

class VacancyDetailsViewModel(
    private val vacancyInteractor: VacancyDetailsInteractor,
    private val dictionaryInteractor: DictionaryInteractor,
    private val favoritesInteractor: FavoritesInteractor,
    private var sharingInteractor: SharingInteractor
) : ViewModel() {

    private val _stateLiveData = MutableLiveData<VacancyDetailsState>()
    val stateLiveData: LiveData<VacancyDetailsState> get() = _stateLiveData

    private val _currentVacancy = MutableLiveData<Vacancy?>(null)
    val currentVacancy = _currentVacancy as LiveData<Vacancy?>

    fun fetchVacancyDetails(vacancyId: String) {
        renderState(VacancyDetailsState.Loading)
        viewModelScope.launch {
            val currencyDictionary = dictionaryInteractor.getCurrencyDictionary()
            vacancyInteractor.getVacancyDetails(vacancyId).collect { result ->
                when (result) {
                    is VacancyDetailStatus.Loading -> renderState(VacancyDetailsState.Loading)

                    is VacancyDetailStatus.Content -> {
                        _currentVacancy.value = result.data
                        val currSymbol = currencyDictionary[result.data?.salary?.currency]?.abbr ?: ""
                        val isFavorite = isVacancyFavorite(vacancyId)
                        renderState(VacancyDetailsState.Content(result.data!!, currSymbol, isFavorite))
                    }

                    is VacancyDetailStatus.NoConnection -> {
                        if (isVacancyFavorite(vacancyId)) {
                            getVacancyFromDb(vacancyId)
                        } else
                            renderState(VacancyDetailsState.Error)
                    }

                    else -> renderState(VacancyDetailsState.Error)
                }
            }
        }
    }

    fun addToFavorite() {
        viewModelScope.launch {
            if (state.isFavorite) {
                favoritesInteractor.removeVacancyFromFavorites(vacancy)
                favoritesInteractor.removeVacancyDetails(vacancyDetails.id)
            } else {
                favoritesInteractor.addVacancyDetails(vacancyDetails)
                favoritesInteractor.addVacancyToFavorites(vacancy)
            }
            renderState(
                VacancyDetailsState.Content(
                    vacancy = vacancyDetails,
                    currencySymbol = state.currencySymbol,
                    isFavorite = !state.isFavorite
                )
            )
        }
    }

    private fun getVacancyFromDb(vacancyId: String) {
        viewModelScope.launch {
            val vacancyFromDb = favoritesInteractor.getVacancyDetails(vacancyId)
            _currentVacancy.value = vacancyFromDb
            renderState(VacancyDetailsState.Content(
                vacancy = vacancyFromDb,
                currencySymbol = null,
                isFavorite = true))
        }
    }

    private suspend fun isVacancyFavorite(vacancyId: String): Boolean {
        return favoritesInteractor.isVacancyFavorite(vacancyId)
    }

    private fun renderState(state: VacancyDetailsState) {
        _stateLiveData.value = state
    }

    fun shareApp(vacancyDetails: VacancyDetails) {
        sharingInteractor.shareApp(vacancyDetails.alternateUrl)
    }

    fun phoneCall(phoneNumber: String) {
        sharingInteractor.phoneCall(phoneNumber)
    }

    fun eMail(email: String) {
        sharingInteractor.eMail(email)
    }
}
