package com.example.myapplication.domain.repository.model

sealed interface Result<out T> {
    class Success<T>(val data: T) : Result<T>
    data class Error(val error: AppError) : Result<Nothing>
    data object Loading : Result<Nothing>
}