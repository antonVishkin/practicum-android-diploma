package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.converters.VacancyEntityConverter
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.domain.api.favorites.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyPage
import kotlin.math.ceil

class FavoritesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val entityConvertet: VacancyEntityConverter
) : FavoritesRepository {
    override suspend fun addVacancyToFavorites(vacancy: Vacancy) {
        appDatabase.favoritesDAO().addVacancy(entityConvertet.map(vacancy))
    }

    override suspend fun getFavoriteVacanciesPage(limit: Int, from: Int): VacancyPage {
        val vacancyList = appDatabase.favoritesDAO().getFavoritesList(limit, from).map { entityConvertet.map(it) }
        val countFavoriteVacancies = appDatabase.favoritesDAO().favoriteCount()
        val fromPages = ceil(countFavoriteVacancies * 1.0 / limit).toInt()
        val currPage = ceil(from * 1.0 / limit).toInt()
        return VacancyPage(vacancyList, currPage, fromPages, countFavoriteVacancies)
    }

    override suspend fun getFavoriteVacancies(): List<Vacancy> {
        return appDatabase.favoritesDAO().getAllFavorites().map { entityConvertet.map(it) }
    }

    override suspend fun removeVacancyFromFavorites(vacancy: Vacancy) {
        appDatabase.favoritesDAO().removeVacancy(entityConvertet.map(vacancy))
    }

    override suspend fun isVacancyFavorite(vacancyId: String): Boolean {
        return appDatabase.favoritesDAO().isVacancyFavorite(vacancyId) != 0
    }

    // Details
    override suspend fun addVacancyDetails(vacancy: Vacancy) {
        return appDatabase.favoritesDAO().addVacancyDetails(entityConvertet.map(vacancy))
    }

    override suspend fun removeVacancyDetails(vacancyId: String) {
        appDatabase.favoritesDAO().removeVacancyDetails(vacancyId)
    }

    override suspend fun getVacancyById(vacancyId: String): Vacancy {
        return entityConvertet.map(appDatabase.favoritesDAO().getVacancyDetails(vacancyId))
    }
}
