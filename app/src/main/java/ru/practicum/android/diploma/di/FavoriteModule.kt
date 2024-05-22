package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.FavoritesRepositoryImpl
import ru.practicum.android.diploma.data.converters.DBConverters
import ru.practicum.android.diploma.domain.api.favorites.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.favorites.FavoritesRepository
import ru.practicum.android.diploma.domain.impl.favorites.FavoritesInteractorImpl
import ru.practicum.android.diploma.ui.favorite.FavoriteViewModel

val favoriteModule = module {
    single<FavoritesRepository> { FavoritesRepositoryImpl(get(), get()) }
    single<FavoritesInteractor> { FavoritesInteractorImpl(get()) }

    viewModel {
        FavoriteViewModel(get(), get())
    }
}
