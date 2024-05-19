package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.data.db.dao.DictionaryDAO
import ru.practicum.android.diploma.data.db.dao.FavoritesDAO

@Database(
    version = 1,
    entities = [VacancyEntity::class,CurrencyDictionaryEntity::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDAO(): FavoritesDAO

    abstract fun dictionaryDAO():DictionaryDAO
}
