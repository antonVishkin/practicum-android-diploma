package ru.practicum.android.diploma.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converters.DBConverters
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.domain.api.favorites.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyPage
import kotlin.math.ceil

class FavoritesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val dBConverters: DBConverters
) : FavoritesRepository {
    override suspend fun addVacancyToFavorites(vacancy: Vacancy) {
        appDatabase.favoritesDAO().addVacancy(dBConverters.map(vacancy))
    }

//    override suspend fun getFavoriteVacancies(limit: Int, from: Int): VacancyPage {
//        val vacancyList = appDatabase.favoritesDAO().getFavoritesList(limit, from).map { dBConverters.map(it) }
//        val countFavoriteVacancies = appDatabase.favoritesDAO().favoriteCount()
//        val fromPages = ceil(countFavoriteVacancies * 1.0 / limit).toInt()
//        val currPage = ceil(from * 1.0 / limit).toInt()
//        return VacancyPage(vacancyList, currPage, fromPages, countFavoriteVacancies)
//    }

    override suspend fun getFavoriteVacancies(): Flow<List<Vacancy>> = flow {
        val favorites = appDatabase.favoritesDAO().getFavoritesList()
        emit(favorites.map { favorite -> dBConverters.map(favorite) })
    }
    override suspend fun removeVacancyFromFavorites(vacancy: Vacancy) {
        appDatabase.favoritesDAO().removeVacancy(dBConverters.map(vacancy))
    }

    override suspend fun isVacancyFavorite(vacancy: Vacancy): Boolean {
        return appDatabase.favoritesDAO().isVacancyFavorite(vacancy.id) != 0
    }
}
