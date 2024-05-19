package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.CurrencyDictionaryRepositoryImpl
import ru.practicum.android.diploma.data.converters.VacancyDBConverters
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.dto.DTOConverters
import ru.practicum.android.diploma.data.network.HeadHunterApi
import ru.practicum.android.diploma.domain.api.dictionary.CurrencyDictionaryRepository
import ru.practicum.android.diploma.domain.api.dictionary.DictionaryInteractor
import ru.practicum.android.diploma.domain.impl.dictionary.DictionaryInteractorImpl

val dataModule = module {
    single<HeadHunterApi> {
        Retrofit.Builder().baseUrl("https://api.hh.ru").addConverterFactory(
            GsonConverterFactory.create()
        ).build().create(HeadHunterApi::class.java)
    }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .build()
    }
    single <DictionaryInteractor>{ DictionaryInteractorImpl(get()) }
    single <CurrencyDictionaryRepository>{ CurrencyDictionaryRepositoryImpl(get(),get()) }
    factory { DTOConverters() }
    factory { VacancyDBConverters() }
}
