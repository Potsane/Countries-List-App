package com.example.myapplication.presentation.country

import com.example.myapplication.data.remote.model.Country
import com.example.myapplication.domain.repository.model.AppError
import com.example.myapplication.domain.repository.model.Result
import com.example.myapplication.domain.repository.CountryRepository

class CountryRepositoryFake : CountryRepository {
    override suspend fun getCountryList(
        isNetworkAvailable: Boolean,
        forceRefresh: Boolean,
        onSuccess: (result: Result<List<Country>>) -> Unit,
        onError: (result: Result<AppError>) -> Unit
    ) {
        val country1 = Country(
            "South Africa",
            mutableListOf("ZAR"),
            mutableListOf("PTA"),
            mutableListOf("Sotho"),
            100000.0,
            5000000L,
            mutableListOf("GMT+2"),
            mutableListOf("Africa"),
            "url"
        )
        val country2 = Country(
            "Zambia",
            mutableListOf("Kwacha"),
            mutableListOf("PTA"),
            mutableListOf("English"),
            100000.0,
            5000000L,
            mutableListOf("GMT+2"),
            mutableListOf("Africa"),
            "url"
        )

        onSuccess(
            Result.Success(listOf(country1, country2))
        )

    }
}