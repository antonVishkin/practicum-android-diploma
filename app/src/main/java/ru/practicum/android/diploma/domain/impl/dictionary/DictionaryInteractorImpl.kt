package ru.practicum.android.diploma.domain.impl.dictionary

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.api.dictionary.CurrencyDictionaryRepository
import ru.practicum.android.diploma.domain.api.dictionary.DictionaryInteractor
import ru.practicum.android.diploma.domain.models.Currency

class DictionaryInteractorImpl(private val dictionaryRepository: CurrencyDictionaryRepository): DictionaryInteractor {
    override fun getCurrencyDictionary(): Flow<List<Currency>> {
        return dictionaryRepository.getCurrencyDictionary()
    }
}
