package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient

val searchModule = module {
    single<RetrofitNetworkClient> { RetrofitNetworkClient(get()) }
}
