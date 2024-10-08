package com.example.myapplication.data.remote

import com.example.myapplication.data.remote.model.CountriesResponse
import com.example.myapplication.data.remote.model.Country
import retrofit2.Response
import retrofit2.http.GET

interface CountryApiService {

    @GET("/v3.1/all")
    suspend fun getCountriesList() : Response<CountriesResponse>
}