package ru.practicum.android.diploma.domain.api.favorites

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyPage

interface FavoritesRepository {
    suspend fun addVacancyToFavorites(vacancy: Vacancy)

    // suspend fun getFavoriteVacancies(limit: Int, from: Int): VacancyPage
    suspend fun getFavoriteVacancies(): Flow<List<Vacancy>>

    suspend fun removeVacancyFromFavorites(vacancy: Vacancy)

    suspend fun isVacancyFavorite(vacancy: Vacancy): Boolean
}
