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

    @Query("SELECT COUNT(*) FROM table_favorites_vacancies WHERE id=:vacancyId")
    suspend fun isVacancyFavorite(vacancyId: String): Int

    @Delete
    suspend fun removeVacancy(vacancyEntity: VacancyEntity)
}
