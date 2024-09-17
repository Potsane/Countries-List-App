package com.example.myapplication.domain.di

import com.example.myapplication.data.client.CountryRepositoryImpl
import com.example.myapplication.domain.repository.CountryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideCountryRepository(countryRepositoryImpl: CountryRepositoryImpl) : CountryRepository
}