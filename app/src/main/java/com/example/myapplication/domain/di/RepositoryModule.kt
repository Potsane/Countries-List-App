package com.example.myapplication.domain.di

import com.example.myapplication.data.client.PokemonRepositoryImpl
import com.example.myapplication.domain.repository.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providePokemonRepository(pokemonRepositoryImpl: PokemonRepositoryImpl) : PokemonRepository
}