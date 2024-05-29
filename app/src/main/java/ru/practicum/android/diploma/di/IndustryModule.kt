package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.IndustryRepositoryImpl
import ru.practicum.android.diploma.domain.api.industry.IndustryInteractor
import ru.practicum.android.diploma.domain.api.industry.IndustryRepository
import ru.practicum.android.diploma.domain.impl.industry.IndustryInteractorImpl
import ru.practicum.android.diploma.ui.filtration.industry.IndustryViewModel

val industryModule = module {
    single<IndustryRepository> { IndustryRepositoryImpl(get()) }
    single<IndustryInteractor> { IndustryInteractorImpl(get()) }
    viewModel {
        IndustryViewModel(get())
    }
}
