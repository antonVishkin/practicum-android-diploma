package ru.practicum.android.diploma.domain.api.favorites

import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyPage

interface FavoritesRepository {
    suspend fun addVacancyToFavorites(vacancy: Vacancy)

    suspend fun getFavoriteVacanciesPage(limit: Int, from: Int): VacancyPage

    suspend fun getFavoriteVacancies(): List<Vacancy>

    suspend fun removeVacancyFromFavorites(vacancy: Vacancy)

    suspend fun isVacancyFavorite(vacancy: Vacancy): Boolean
}
