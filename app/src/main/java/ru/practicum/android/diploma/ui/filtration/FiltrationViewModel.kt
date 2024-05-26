package ru.practicum.android.diploma.ui.filtration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.ui.filtration.FiltrationState.Content

class FiltrationViewModel : ViewModel() {
    private val _state = MutableLiveData<FiltrationState>()
    val state: LiveData<FiltrationState> get() = _state

    init {
        _state.value = FiltrationState.Empty
    }


    fun saveState(){
        if (state.value !is Content){

        }
    }

    private fun renderState(state: FiltrationState) {
        _state.value = state
    }
}
