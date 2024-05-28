package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.sharing.ExternalNavigatorImpl
import ru.practicum.android.diploma.domain.sharing.ExternalNavigator
import ru.practicum.android.diploma.domain.sharing.SharingInteractor
import ru.practicum.android.diploma.domain.sharing.SharingInteractorImpl

val sharingModule = module {
    single<ExternalNavigator> { ExternalNavigatorImpl(get()) }
    single<SharingInteractor> { SharingInteractorImpl(get()) }
}
