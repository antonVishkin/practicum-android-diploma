package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.ui.search.SearchViewModel

val searchModule = module {
    single<RetrofitNetworkClient> { RetrofitNetworkClient(get()) }

    viewModel {
        SearchViewModel(get())
    }
}
