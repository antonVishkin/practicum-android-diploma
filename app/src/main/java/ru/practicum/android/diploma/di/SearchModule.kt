package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.SearchRepositoryImpl
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.api.search.SearchInteractor
import ru.practicum.android.diploma.domain.api.search.SearchRepository
import ru.practicum.android.diploma.domain.impl.SearchInteractorImpl
import ru.practicum.android.diploma.ui.search.SearchViewModel

val searchModule = module {
    single<RetrofitNetworkClient> { RetrofitNetworkClient(get()) }

    viewModel {
        SearchViewModel(get())
    }

    single<SearchInteractor> { SearchInteractorImpl(get()) }
    single<SearchRepository> { SearchRepositoryImpl(get(), get()) }
}
