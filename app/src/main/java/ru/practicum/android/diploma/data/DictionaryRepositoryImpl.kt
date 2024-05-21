package ru.practicum.android.diploma.data

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.data.converters.DBConverters
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.domain.api.dictionary.DictionaryRepository
import ru.practicum.android.diploma.domain.models.Currency
import java.io.IOException

class DictionaryRepositoryImpl(private val appDatabase: AppDatabase, private val dbConverters: DBConverters) :
    DictionaryRepository {
    override suspend fun getCurrency(code: String): Currency? {
        return try {
            dbConverters.map(appDatabase.dictionaryDAO().getCurrencyByCode(code))
        } catch (e: IOException) {
            Log.e("DATABASE EMPTY", e.toString())
            null
        }
    }

    override fun getCurrencyDictionary(): Flow<List<Currency>> = flow {
        emit(appDatabase.dictionaryDAO().getCurrencyDictionary().map { dbConverters.map(it) })
    }

    override suspend fun addCurrencies(currencies: List<Currency>) {
        appDatabase.dictionaryDAO().addCurrencies(currencies.map { dbConverters.map(it) })
    }
}
