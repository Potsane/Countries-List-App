package com.example.myapplication.presentation.pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.Result
import com.example.myapplication.domain.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    fun getPokemonList() {
        viewModelScope.launch {
            repository.getPokemonList(
                onSuccess = { countries ->
                    println("============ ->> ${(countries as Result.Success).data}")
                }
            ) { error ->
                println("============ ->> $error")
            }
        }
    }
}