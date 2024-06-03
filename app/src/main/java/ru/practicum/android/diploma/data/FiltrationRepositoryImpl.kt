package ru.practicum.android.diploma.data

import ru.practicum.android.diploma.data.preferences.PreferencesProvider
import ru.practicum.android.diploma.domain.api.filtration.FiltrationRepository
import ru.practicum.android.diploma.domain.models.Filtration

class FiltrationRepositoryImpl(private val preferencesProvider: PreferencesProvider) : FiltrationRepository {
    override suspend fun saveFiltration(filtration: Filtration?) {
        preferencesProvider.saveFiltration(filtration)
    }

    override suspend fun getFiltration(): Filtration? {
        return preferencesProvider.getFiltration()
    }
}
