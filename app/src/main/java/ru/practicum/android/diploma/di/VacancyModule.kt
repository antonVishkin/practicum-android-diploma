package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.VacancyDetailsRepositoryImpl
import ru.practicum.android.diploma.data.network.RetrofitNetworkClient
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsInteractor
import ru.practicum.android.diploma.domain.api.details.VacancyDetailsRepository
import ru.practicum.android.diploma.domain.impl.details.VacancyDetailsInteractorImpl
import ru.practicum.android.diploma.ui.vacancies.VacancyViewModel

val vacancyModule = module {
    single<RetrofitNetworkClient> { RetrofitNetworkClient(get()) }
    single<VacancyDetailsRepository> { VacancyDetailsRepositoryImpl(get(), get()) }
    factory<VacancyDetailsInteractor> { VacancyDetailsInteractorImpl(get()) }
    viewModel { VacancyViewModel(get()) }
}
