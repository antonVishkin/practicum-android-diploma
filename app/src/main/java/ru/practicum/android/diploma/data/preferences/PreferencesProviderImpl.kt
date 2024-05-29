package ru.practicum.android.diploma.data.preferences

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.domain.models.Filtration

class PreferencesProviderImpl(
    private val gson: Gson,
    private val prefs: SharedPreferences,
) : PreferencesProvider {
    override suspend fun saveFiltration(filtration: Filtration) {
        val filtrationNullCheck =
            filtration.salary == null && filtration.area == null && filtration.industry == null
        if (filtrationNullCheck && !filtration.onlyWithSalary) {
            return
        } else {
            prefs.edit().putString(FILTRATION_LABEL, gson.toJson(filtration)).apply()
        }
    }

    override suspend fun getFiltration(): Filtration? {
        val filtrationString = prefs.getString(FILTRATION_LABEL, "")
        val itemType = object : TypeToken<Filtration>() {}.type
        val filtration = gson.fromJson<Filtration>(filtrationString, itemType)
        return filtration
    }

    companion object {
        const val FILTRATION_LABEL = "FILTRATION_LABEL"
    }
}