package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.converters.VacancyDBConverters
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val vacancyDBConverters: VacancyDBConverters
) : FavoritesRepository {
    override suspend fun addVacancyToFavorites(vacancy: Vacancy) {
        appDatabase.favoritesDAO().addVacancy(vacancyDBConverters.map(vacancy))
    }

    override suspend fun getFavoriteVacancies(limit: Int, from: Int): List<Vacancy> {
        return appDatabase.favoritesDAO().getFavoritesList(limit, from).map { vacancyDBConverters.map(it) }
    }

    override suspend fun removeVacancyFromFavorites(vacancy: Vacancy) {
        appDatabase.favoritesDAO().removeVacancy(vacancyDBConverters.map(vacancy))
    }

    override suspend fun isVacancyFavorite(vacancy: Vacancy): Boolean {
        return appDatabase.favoritesDAO().isVacancyFavorite(vacancy.id) != 0
    }
}
