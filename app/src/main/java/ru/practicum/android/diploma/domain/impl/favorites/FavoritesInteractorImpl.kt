package ru.practicum.android.diploma.domain.impl.favorites

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.domain.api.favorites.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.favorites.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyPage

class FavoritesInteractorImpl(private val favoritesRepository: FavoritesRepository) : FavoritesInteractor {
    override suspend fun addVacancyToFavorites(vacancy: Vacancy) {
        favoritesRepository.addVacancyToFavorites(vacancy)
    }

//    override suspend fun getFavoriteVacancies(limit: Int, from: Int): VacancyPage {
//        return favoritesRepository.getFavoriteVacancies(limit, from)
//    }

    override suspend fun getFavoriteVacancies(): Flow<List<Vacancy>> {
        return favoritesRepository.getFavoriteVacancies().map { vacancy -> vacancy.reversed() }
    }

    override suspend fun removeVacancyFromFavorites(vacancy: Vacancy) {
        favoritesRepository.removeVacancyFromFavorites(vacancy)
    }

    override suspend fun isVacancyFavorite(vacancy: Vacancy): Boolean {
        return favoritesRepository.isVacancyFavorite(vacancy)
    }
}
