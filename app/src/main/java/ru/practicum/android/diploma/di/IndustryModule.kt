package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.IndustryRepositoryImpl
import ru.practicum.android.diploma.domain.api.industry.IndustryInteractor
import ru.practicum.android.diploma.domain.api.industry.IndustryRepository
import ru.practicum.android.diploma.domain.impl.industry.IndustryInteractorImpl

val industryModule = module {
    single<IndustryRepository> { IndustryRepositoryImpl(get()) }
    factory<IndustryInteractor> { IndustryInteractorImpl(get()) }
}
