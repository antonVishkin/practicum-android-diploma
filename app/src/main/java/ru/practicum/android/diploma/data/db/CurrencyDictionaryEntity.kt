package ru.practicum.android.diploma.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_dictionary")
data class CurrencyDictionaryEntity(
    @PrimaryKey
    val code:String,
    val name:String,
    val abbr:String,
)
