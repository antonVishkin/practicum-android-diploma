package ru.practicum.android.diploma.domain.api.favorites

import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetails
import ru.practicum.android.diploma.domain.models.VacancyPage

interface FavoritesRepository {
    suspend fun addVacancyToFavorites(vacancy: Vacancy)

    suspend fun getFavoriteVacanciesPage(limit: Int, from: Int): VacancyPage

    suspend fun getFavoriteVacancies(): List<Vacancy>

    suspend fun removeVacancyFromFavorites(vacancy: Vacancy)

    suspend fun isVacancyFavorite(vacancyId: String): Boolean

    // Details

    suspend fun addVacancyDetails(vacancyDetails: VacancyDetails)

    suspend fun getVacancyDetails(vacancyId: String): VacancyDetails

    suspend fun removeVacancyDetails(vacancyId: String)

    suspend fun getVacancyById(vacancyId: String): Vacancy
}
