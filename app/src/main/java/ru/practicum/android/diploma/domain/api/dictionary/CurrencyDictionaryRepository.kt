package ru.practicum.android.diploma.domain.api.dictionary

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Currency

interface CurrencyDictionaryRepository {
    fun getCurrencyDictionary():Flow<List<Currency>>
}
