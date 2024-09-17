package com.example.myapplication.domain.repository

import com.example.myapplication.data.model.Country
import com.example.myapplication.domain.model.AppError
import com.example.myapplication.domain.model.Result

interface CountryRepository {

    suspend fun getCountryList(
        isNetworkAvailable: Boolean,
        forceRefresh: Boolean = true,
        onSuccess: (result: Result<List<Country>>) -> Unit,
        onError: (result: Result<AppError>) -> Unit
    )
}