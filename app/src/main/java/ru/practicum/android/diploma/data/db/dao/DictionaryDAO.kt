package ru.practicum.android.diploma.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.db.CurrencyDictionaryEntity
import ru.practicum.android.diploma.domain.models.Currency

@Dao
interface DictionaryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCurrencies(currencies:List<CurrencyDictionaryEntity>)

    @Query("SELECT * FROM currency_dictionary WHERE code = :code")
    suspend fun getCurrencyByCode(code:String):CurrencyDictionaryEntity

    @Query("SELECT * FROM currency_dictionary")
    suspend fun getCurrencyDictionary():List<CurrencyDictionaryEntity>
}
