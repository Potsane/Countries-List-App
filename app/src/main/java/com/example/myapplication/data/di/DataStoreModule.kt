package com.example.myapplication.data.di

import com.example.myapplication.data.local.preferences.CountriesAppDataStore
import com.example.myapplication.data.local.preferences.CountriesAppDataStoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataStoreModule {

    @Binds
    fun provideCountriesAppDataStore(
        countriesAppDataStoreImpl: CountriesAppDataStoreImpl
    ): CountriesAppDataStore
}