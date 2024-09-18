package com.example.myapplication.data.client

import com.example.myapplication.data.local.database.CountryDao
import com.example.myapplication.data.local.preferences.CountriesAppDataStore
import com.example.myapplication.data.remote.model.CountriesResponse
import com.example.myapplication.data.remote.model.Country
import com.example.myapplication.data.remote.CountryApiService
import com.example.myapplication.domain.repository.model.AppError
import com.example.myapplication.domain.repository.model.Result
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.times
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class CountryRepositoryTest {
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    private val apiService = mock<CountryApiService>()
    private val countryDao = mock<CountryDao>()
    private val dataStore = mock<CountriesAppDataStore>()
    private val repository = CountryRepositoryImpl(apiService, countryDao, dataStore)

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should return error if is offline and there is not cached data`() =
        scope.runTest {
            whenever(countryDao.isEmpty()).thenReturn(true)
            val onSuccess: (Result<List<Country>>) -> Unit = mock()
            val onError: (Result<AppError>) -> Unit = mock()
            repository.getCountryList(
                isNetworkAvailable = false,
                forceRefresh = true,
                onSuccess = onSuccess,
                onError = onError
            )
            verify(onError).invoke(Result.Error(AppError.NotConnected))
        }

    @Test
    fun `should fetch data from api service`() = scope.runTest {
        val apiResponse: Response<CountriesResponse> = mock()
        whenever(apiService.getCountriesList()).thenReturn(apiResponse)

        val onSuccess: (Result<List<Country>>) -> Unit = mock()
        val onError: (Result<AppError>) -> Unit = mock()
        repository.getCountryList(
            isNetworkAvailable = true,
            forceRefresh = true,
            onSuccess = onSuccess,
            onError = onError
        )

        verify(apiService).getCountriesList()
    }
    @Test
    fun `should fetch data from database`() = scope.runTest {
        val country = Country(
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
        val countryList = listOf(country)
        whenever(countryDao.isEmpty()).thenReturn(false)
        whenever(countryDao.getAllItems()).thenReturn(countryList)
        val onSuccess: (Result<List<Country>>) -> Unit = mock()
        val onError: (Result<AppError>) -> Unit = mock()
        repository.getCountryList(
            isNetworkAvailable = false,
            forceRefresh = false,
            onSuccess = onSuccess,
            onError = onError
        )

        verify(countryDao).getAllItems()
        verify(onSuccess, times(1)).invoke(any())
    }
}