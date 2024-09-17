package com.example.myapplication.data.client

import com.example.myapplication.data.local.database.CountryDao
import com.example.myapplication.data.local.preferences.CountriesAppDataStore
import com.example.myapplication.data.model.Country
import com.example.myapplication.data.remote.CountryApiService
import com.example.myapplication.domain.model.AppError
import com.example.myapplication.domain.model.Result
import com.example.myapplication.domain.repository.CountryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.until

class CountryRepositoryImpl @Inject constructor(
    private val apiService: CountryApiService,
    private val countryDao: CountryDao,
    private val dataStore: CountriesAppDataStore,
) : CountryRepository {

    override suspend fun getCountryList(
        isNetworkAvailable: Boolean,
        forceRefresh: Boolean,
        onSuccess: (result: Result<List<Country>>) -> Unit,
        onError: (result: Result<AppError>) -> Unit
    ) {
        withContext(Dispatchers.IO) {
            try {
                val countries: List<Country> = when {
                    cantFullFillRequest(isNetworkAvailable) -> {
                        launch(Dispatchers.Main) { onError(Result.Error(AppError.NotConnected)) }
                        return@withContext
                    }
                    shouldFetchDataFromApiService(forceRefresh, isNetworkAvailable) -> getDataFromApiService()
                    else -> getDataFromDatabase()
                }
                launch(Dispatchers.Main) { onSuccess(Result.Success(countries)) }
            } catch (error: AppError) {
                launch(Dispatchers.Main) { onError(Result.Error(error)) }
            }
        }
    }

    override suspend fun searchCountry(
        onSuccess: (result: Result<List<Country>>) -> Unit,
        onError: (result: Result<AppError>) -> Unit
    ) {
        try {
            val countries = countryDao.getAllItems()
            if (countries.isNullOrEmpty()) {
                onError(Result.Error(AppError.EmptyResponse))
            } else {
                onSuccess(Result.Success(countries))
            }
        } catch (exception: Exception) {
            onError(Result.Error(AppError.EmptyResponse))
        }
    }

    private suspend fun getDataFromApiService(): List<Country> {
        try {
            val countries = apiService.getCountriesList().body()?.countries
            if (countries.isNullOrEmpty()) throw AppError.EmptyResponse
            persistProductsLocally(countries)
            return countries
        } catch (exception: Exception) {
            throw AppError.GenericError
        }
    }

    private suspend fun getDataFromDatabase(): List<Country> {
        try {
            val countries = countryDao.getAllItems()
            if (countries.isNullOrEmpty()) throw AppError.EmptyResponse
            return countries
        } catch (exception: Exception) {
            throw AppError.GenericError
        }
    }

    private suspend fun isLocalProductDataOutdated(): Boolean {
        val saveTime = dataStore.getCountriesPersistenceTimeStamp()
        val now = Clock.System.now()
        val diff = Instant.fromEpochMilliseconds(saveTime)
            .until(now, DateTimeUnit.TimeBased(60_000_000_000))
        return diff > 5
    }

    private suspend fun persistProductsLocally(items: List<Country>) {
        countryDao.clearAndInsert(items)
        val currentTime = Clock.System.now().toEpochMilliseconds()
        dataStore.setCountriesPersistenceTimeStamp(currentTime)
    }

    private suspend fun shouldFetchDataFromApiService(
        forceRefresh: Boolean,
        isNetworkAvailable: Boolean
    ) = isNetworkAvailable && (forceRefresh || isLocalProductDataOutdated())

    private suspend fun cantFullFillRequest(isNetworkAvailable: Boolean): Boolean {
        return !isNetworkAvailable && countryDao.isEmpty()
    }
}