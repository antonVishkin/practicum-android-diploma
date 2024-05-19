package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.data.CurrencyRepositoryImpl
import ru.practicum.android.diploma.data.DictionaryRepositoryImpl
import ru.practicum.android.diploma.data.converters.DBConverters
import ru.practicum.android.diploma.data.db.AppDatabase
import ru.practicum.android.diploma.data.dto.DTOConverters
import ru.practicum.android.diploma.data.network.HeadHunterApi
import ru.practicum.android.diploma.domain.api.dictionary.CurrencyRepository
import ru.practicum.android.diploma.domain.api.dictionary.DictionaryInteractor
import ru.practicum.android.diploma.domain.api.dictionary.DictionaryRepository
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
    single<DictionaryInteractor> { DictionaryInteractorImpl(get(), get()) }
    single<CurrencyRepository> { CurrencyRepositoryImpl(get(), get()) }
    single<DictionaryRepository> { DictionaryRepositoryImpl(get(), get()) }
    factory { DTOConverters() }
    factory { DBConverters() }
}
