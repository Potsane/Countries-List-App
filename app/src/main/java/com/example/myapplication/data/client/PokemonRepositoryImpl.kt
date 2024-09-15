package com.example.myapplication.data.client

import com.example.myapplication.data.remote.CountryApiService
import com.example.myapplication.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val apiService: CountryApiService
) : PokemonRepository {
    override suspend fun getPokemonList() {
        withContext(Dispatchers.IO) {
           val list = apiService.getCountriesList().body()?.countries
            println("====================")
            println(list)
            println("====================")
        }
    }
}