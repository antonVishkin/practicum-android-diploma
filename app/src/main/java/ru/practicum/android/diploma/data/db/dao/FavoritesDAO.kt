package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.data.db.VacancyEntity

@Dao
interface FavoritesDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addVacancy(vacancyEntity: VacancyEntity)

    @Query("SELECT * FROM table_favorites_vacancies LIMIT :limit OFFSET :from")
    suspend fun getFavoritesList(limit: Int, from: Int): List<VacancyEntity>

    @Query("SELECT * FROM table_favorites_vacancies")
    suspend fun getAllFavorites(): List<VacancyEntity>

    @Query("SELECT COUNT(*) FROM table_favorites_vacancies WHERE id=:vacancyId")
    suspend fun isVacancyFavorite(vacancyId: String): Int

    @Query("SELECT COUNT(*) FROM table_favorites_vacancies")
    suspend fun favoriteCount(): Int

    @Delete
    suspend fun removeVacancy(vacancyEntity: VacancyEntity)

    // DetailsEntity
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addVacancyDetails(vacancyDetailsEntity: VacancyEntity)

    @Query("SELECT * FROM table_favorites_vacancies WHERE id=:vacancyId")
    suspend fun getVacancyDetails(vacancyId: String): VacancyEntity

    @Query("DELETE FROM table_favorites_vacancies WHERE id=:vacancyId")
    suspend fun removeVacancyDetails(vacancyId: String)
}
