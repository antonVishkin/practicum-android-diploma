package ru.practicum.android.diploma.ui.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.domain.api.favorites.FavoritesInteractor

class FavoriteViewModel(private val favoritesInteractor: FavoritesInteractor) : ViewModel() {
    private val _stateFavorite = MutableLiveData<FavoriteState>(FavoriteState.Empty)
    val stateFavorite: LiveData<FavoriteState> get() = _stateFavorite
    private var from = 0

    init {
        fillData()
    }

    private fun fillData() {
        renderState(FavoriteState.Loading)
        getFavorites()
    }

    fun getNewPage() {
        renderState(FavoriteState.LoadingNewPage)
        if (_stateFavorite.value is FavoriteState.Content) {
            val page = (_stateFavorite.value as FavoriteState.Content).vacancyPage
            if (page.currPage < page.fromPages) getFavorites()
        }
    }

    private fun getFavorites() {
        viewModelScope.launch {
            Log.d("FAVORITE", "from $from limit $LIMIT")
            val vacancyPage = favoritesInteractor.getFavoriteVacancies(LIMIT, from)
            from = vacancyPage.currPage * LIMIT
            Log.d("FAVORITE", "${vacancyPage.vacancyList}")
            renderState(FavoriteState.Content(vacancyPage))
        }
    }

    private fun renderState(state: FavoriteState) {
        _stateFavorite.value = state
    }

    companion object {
        private const val LIMIT = 20
    }
}