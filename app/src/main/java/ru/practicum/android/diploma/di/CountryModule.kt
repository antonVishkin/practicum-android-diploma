package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.CountryRepositoryImpl
import ru.practicum.android.diploma.domain.api.filtration.country.CountryInteractor
import ru.practicum.android.diploma.domain.api.filtration.country.CountryRepository
import ru.practicum.android.diploma.domain.impl.country.CountryInteractorImpl
import ru.practicum.android.diploma.ui.filtration.country.CountryViewModel

val сountryModule = module {
    single<CountryRepository> { CountryRepositoryImpl(get()) }
    factory<CountryInteractor> { CountryInteractorImpl(get()) }
    viewModel { CountryViewModel(get()) }
}
