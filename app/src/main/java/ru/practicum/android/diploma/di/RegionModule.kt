package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.data.RegionRepositoryImpl
import ru.practicum.android.diploma.domain.api.filtration.region.RegionInteractor
import ru.practicum.android.diploma.domain.api.filtration.region.RegionRepository
import ru.practicum.android.diploma.domain.impl.region.RegionInteractorImpl
import ru.practicum.android.diploma.ui.filtration.country.CountryViewModel
import ru.practicum.android.diploma.ui.filtration.region.RegionViewModel

val regionModule = module {
    single<RegionRepository> { RegionRepositoryImpl(get()) }
    factory<RegionInteractor> { RegionInteractorImpl(get()) }
    viewModel { RegionViewModel(get()) }
}
