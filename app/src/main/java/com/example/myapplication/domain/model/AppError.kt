package com.example.myapplication.domain.model

sealed class AppError(private val details: String) : Throwable(details) {
    data object EmptyResponse : AppError("Search Query Returned Nothing")
    data object NotConnected : AppError("No Internet Connection")
    data object GenericError : AppError("Something went wrong, we'll look into it")
}