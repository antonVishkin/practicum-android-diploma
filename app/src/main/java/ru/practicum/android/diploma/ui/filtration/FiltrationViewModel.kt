package ru.practicum.android.diploma.ui.filtration

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.filtration.FiltrationInteractor
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Filtration
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.ui.filtration.FiltrationState.Content
import ru.practicum.android.diploma.util.debounce

class FiltrationViewModel(private val filtrationInteractor: FiltrationInteractor) : ViewModel() {
    private val _state = MutableLiveData<FiltrationState>()
    val state: LiveData<FiltrationState> get() = _state
    val salaryDebounce = debounce<String>(SALARY_DEBOUNCE, viewModelScope, true) {
        setSalary(it)
    }

    init {
        _state.value = FiltrationState.Empty
    }

    private fun saveStateToPrefs(state: FiltrationState) {
        val filtration = (state as Content).filtration
        viewModelScope.launch {
            filtrationInteractor.saveFiltration(filtration)
        }
    }

    fun getFiltrationFromPrefs() {
        viewModelScope.launch {
            val filtration = filtrationInteractor.getFiltration()
            Log.v("FILTRATION","get $filtration")
            Log.v("FILTRATION","get industry ${filtration?.industry}")
            if (filtration != null) {
                renderState(
                    Content(
                        filtration
                    )
                )
            } else {
                renderState(
                    FiltrationState.Empty
                )
            }
        }
    }

    fun setCheckbox(onlyWithSalary: Boolean) {
        val filtration = getCurrFiltration()
        renderState(
            Content(
                Filtration(
                    area = filtration.area,
                    industry = filtration.industry,
                    salary = filtration.salary,
                    onlyWithSalary = onlyWithSalary
                )
            )
        )
    }

    fun setSalary(salary: String) {
        val filtration = getCurrFiltration()
        renderState(
            Content(
                Filtration(
                    area = filtration.area,
                    industry = filtration.industry,
                    salary = salary,
                    onlyWithSalary = filtration.onlyWithSalary
                )
            )
        )
    }

    fun setArea(area: String?) {
        val filtration = getCurrFiltration()
        renderState(
            Content(
                Filtration(
                    area = if (area == null) null else Area(id = area, ""),
                    industry = filtration.industry,
                    salary = filtration.salary,
                    onlyWithSalary = filtration.onlyWithSalary
                )
            )
        )
    }

    fun setIndustry(industry: Industry?) {
        val filtration = getCurrFiltration()
        renderState(
            Content(
                Filtration(
                    area = filtration.area,
                    industry = industry,
                    salary = filtration.salary,
                    onlyWithSalary = filtration.onlyWithSalary
                )
            )
        )
    }

    private fun renderState(state: FiltrationState) {
        _state.value = state
        saveStateToPrefs(state)
    }

    private fun getCurrFiltration(): Filtration {
        return if (state.value is Content) {
            (state.value as Content).filtration
        } else {
            Filtration(null, null, null, false)
        }
    }

    companion object {
        private const val SALARY_DEBOUNCE = 2000L
    }
}
