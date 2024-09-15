package com.example.myapplication.domain.repository

interface PokemonRepository {

    suspend fun getPokemonList()
}