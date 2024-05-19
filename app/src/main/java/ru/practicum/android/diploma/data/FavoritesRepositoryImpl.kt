package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.converters.DBConverters
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val dBConverters: DBConverters
) : FavoritesRepository {
    override suspend fun addVacancyToFavorites(vacancy: Vacancy) {
        appDatabase.favoritesDAO().addVacancy(dBConverters.map(vacancy))
    }

    override suspend fun getFavoriteVacancies(limit: Int, from: Int): List<Vacancy> {
        return appDatabase.favoritesDAO().getFavoritesList(limit, from).map { dBConverters.map(it) }
    }

    override suspend fun removeVacancyFromFavorites(vacancy: Vacancy) {
        appDatabase.favoritesDAO().removeVacancy(dBConverters.map(vacancy))
    }

    override suspend fun isVacancyFavorite(vacancy: Vacancy): Boolean {
        return appDatabase.favoritesDAO().isVacancyFavorite(vacancy.id) != 0
    }
}
