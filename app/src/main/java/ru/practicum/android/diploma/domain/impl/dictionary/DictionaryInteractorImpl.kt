package ru.practicum.android.diploma.domain.impl.dictionary

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.dictionary.CurrencyRepository
import ru.practicum.android.diploma.domain.api.dictionary.DictionaryInteractor
import ru.practicum.android.diploma.domain.api.dictionary.DictionaryRepository
import ru.practicum.android.diploma.domain.models.Currency

class DictionaryInteractorImpl(
    private val currencyRepository: CurrencyRepository,
    private val dictionaryRepository: DictionaryRepository
) : DictionaryInteractor {
    override fun getCurrencyDictionary(): Map<String, Currency> {
        val dictionary = mutableMapOf<String, Currency>()
        GlobalScope.launch {
            val dbDictionary = mutableListOf<Currency>()
            dictionaryRepository.getCurrencyDictionary().collect {
                dbDictionary.addAll(it)
            }
            if (dbDictionary.isNullOrEmpty()) {
                currencyRepository.getCurrencyDictionary().collect {
                    dbDictionary.addAll(it)
                }
            }
            dbDictionary.forEach { dictionary.put(it.code, it) }
        }
        return dictionary
    }

    override suspend fun getCurrency(code: String): Currency {
        var currency = dictionaryRepository.getCurrency(code)
        if (currency == null) {
            currencyRepository.getCurrencyDictionary().collect {
                dictionaryRepository.addCurrencies(it)
            }
            currency = dictionaryRepository.getCurrency(code)
        }
        return currency ?: Currency(code, "", "")
    }
}
