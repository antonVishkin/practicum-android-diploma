package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.RegionsRepositoryImpl
import ru.practicum.android.diploma.domain.api.filtration.region.RegionsInteractor
import ru.practicum.android.diploma.domain.api.filtration.region.RegionsRepository
import ru.practicum.android.diploma.domain.impl.region.RegionsInteractorImpl
import ru.practicum.android.diploma.ui.filtration.region.RegionsViewModel


val regionModule = module {
    single<RegionsRepository> { RegionsRepositoryImpl(get()) }
    factory<RegionsInteractor> { RegionsInteractorImpl(get()) }
    viewModel { RegionsViewModel(get()) }
}
