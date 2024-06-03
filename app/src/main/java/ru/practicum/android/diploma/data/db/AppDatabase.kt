package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.practicum.android.diploma.data.converters.ConverterForListOfDb
import ru.practicum.android.diploma.data.db.dao.DictionaryDAO
import ru.practicum.android.diploma.data.db.dao.FavoritesDAO

@Database(
    version = 1,
    entities = [VacancyEntity::class, CurrencyDictionaryEntity::class],
    exportSchema = false
)
@TypeConverters(ConverterForListOfDb::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDAO(): FavoritesDAO

    abstract fun dictionaryDAO(): DictionaryDAO
}
