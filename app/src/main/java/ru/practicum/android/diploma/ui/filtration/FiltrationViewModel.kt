package ru.practicum.android.diploma.ui.filtration

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.filtration.FiltrationInteractor
import ru.practicum.android.diploma.domain.models.Area
import ru.practicum.android.diploma.domain.models.Country
import ru.practicum.android.diploma.domain.models.Filtration
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.ui.filtration.FiltrationState.Content

class FiltrationViewModel(private val filtrationInteractor: FiltrationInteractor) : ViewModel() {
    private val _state = MutableLiveData<FiltrationState>()
    val state: LiveData<FiltrationState> get() = _state
    private val _isChanged = MutableLiveData<Boolean>(false)
    val isChanged: LiveData<Boolean> get() = _isChanged

    init {
        _state.value = FiltrationState.Empty
    }

    private fun saveStateToPrefs(state: FiltrationState) {
        viewModelScope.launch {
            if (state !is FiltrationState.Empty) {
                val filtration = (state as Content).filtration
                filtrationInteractor.saveFiltration(filtration)
            } else {
                filtrationInteractor.saveFiltration(null)
            }
        }
    }

    fun getFiltrationFromPrefs() {
        viewModelScope.launch {
            val filtration = filtrationInteractor.getFiltration()
            Log.v("FILTRATION", "get $filtration")
            Log.v("FILTRATION", "get industry ${filtration?.industry}")
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
        _isChanged.value = true
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
        _isChanged.value = true
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

    fun setArea(area: Country?) {
        val filtration = getCurrFiltration()
        _isChanged.value = true
        renderState(
            Content(
                Filtration(
                    area = area,
                    industry = filtration.industry,
                    salary = filtration.salary,
                    onlyWithSalary = filtration.onlyWithSalary
                )
            )
        )
    }

    fun setIndustry(industry: Industry?) {
        val filtration = getCurrFiltration()
        _isChanged.value = true
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

    private fun isEmpty(filtration: Filtration): Boolean {
        val checkFiltrationNull = filtration.industry == null && filtration.area == null
        return checkFiltrationNull && filtration.salary.isNullOrEmpty() && !filtration.onlyWithSalary
    }

    private fun renderState(state: FiltrationState) {
        if (state is Content) {
            if (isEmpty(state.filtration)) {
                _state.value = FiltrationState.Empty
            } else {
                _state.value = state
            }
        } else {
            _state.value = state
        }
        saveStateToPrefs(state)
    }

    private fun getCurrFiltration(): Filtration {
        return if (state.value is Content) {
            (state.value as Content).filtration
        } else {
            Filtration(null, null, null, false)
        }
    }

    fun setEmpty() {
        renderState(FiltrationState.Empty)
    }

    companion object {
        private const val SALARY_DEBOUNCE = 2000L
    }
}
