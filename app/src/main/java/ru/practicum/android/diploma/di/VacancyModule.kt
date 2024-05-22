package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.impl.details.VacancyDetailsInteractorImpl

val vacancyModule = module {
    single<VacancyDetailsRepository> { VacancyDetailsRepositoryImpl(get(), get()) }
    single<VacancyDetailsInteractor> { VacancyDetailsInteractorImpl(get()) }

}
