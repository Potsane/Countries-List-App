package com.example.myapplication.domain.repository

import com.example.myapplication.data.model.Country
import com.example.myapplication.domain.model.AppError
import com.example.myapplication.domain.model.Result

interface PokemonRepository {

    suspend fun getPokemonList(
        forceRefresh: Boolean = false,
        onSuccess: (result: Result<List<Country>>) -> Unit,
        onError: (result: Result<AppError>) -> Unit
    )

    suspend fun searchCountry(
        onSuccess: (result: Result<List<Country>>) -> Unit,
        onError: (result: Result<AppError>) -> Unit
    )
}