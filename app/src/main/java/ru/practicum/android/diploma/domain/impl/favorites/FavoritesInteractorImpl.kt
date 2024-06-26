package ru.practicum.android.diploma.domain.impl.favorites

import ru.practicum.android.diploma.domain.api.favorites.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.favorites.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyPage

class FavoritesInteractorImpl(private val favoritesRepository: FavoritesRepository) : FavoritesInteractor {
    override suspend fun addVacancyToFavorites(vacancy: Vacancy) {
        favoritesRepository.addVacancyToFavorites(vacancy)
    }

    override suspend fun getFavoriteVacanciesPage(limit: Int, from: Int): VacancyPage {
        return favoritesRepository.getFavoriteVacanciesPage(limit, from)
    }

    override suspend fun getFavoriteVacancies(): List<Vacancy> {
        return favoritesRepository.getFavoriteVacancies()
    }

    override suspend fun removeVacancyFromFavorites(vacancy: Vacancy) {
        favoritesRepository.removeVacancyFromFavorites(vacancy)
    }

    override suspend fun isVacancyFavorite(vacancyId: String): Boolean {
        return favoritesRepository.isVacancyFavorite(vacancyId)
    }

    // Details

    override suspend fun addVacancyDetails(vacancy: Vacancy) {
        favoritesRepository.addVacancyDetails(vacancy)
    }

    override suspend fun removeVacancyDetails(vacancyId: String) {
        favoritesRepository.removeVacancyDetails(vacancyId)
    }

    override suspend fun getVacancyById(vacancyId: String): Vacancy {
        return favoritesRepository.getVacancyById(vacancyId)
    }
}
