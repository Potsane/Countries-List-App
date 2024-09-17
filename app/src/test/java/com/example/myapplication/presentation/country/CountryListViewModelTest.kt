package com.example.myapplication.presentation.country

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.myapplication.BaseTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class CountryListViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = BaseTestRule()

    private val repository = CountryRepositoryFake()
    private val viewModel = CountryListViewModel(repository)

    @Test
    fun `should show error if forced to refresh while there is no network`() {
        viewModel.getCountryList(isNetworkAvailable = false, forceRefresh = true)
        viewModel.showError.value?.let {
            assertTrue(it)
        }
    }

    @Test
    fun `should not make call to fetch data if forced to refresh and is offline`() {
        viewModel.getCountryList(isNetworkAvailable = false, forceRefresh = true)
        viewModel.countries.value?.let {
            assertEquals(0, it.size)
        }
    }

    @Test
    fun `should fetch list of countries`() = testCoroutineRule.runBlockingTest {
        viewModel.getCountryList(isNetworkAvailable = true, forceRefresh = false)
        viewModel.countries.value?.let {
            assertEquals(2, it.size)
        }
    }

    @Test
    fun `refresh should fetch all the data`() {
        viewModel.onRefresh { }
        viewModel.countries.value?.let {
            assertEquals(2, it.size)
        }
    }

    @Test
    fun `search query update should filter list`(){
        viewModel.getCountryList(isNetworkAvailable = true, forceRefresh = false)
        viewModel.searchQuery.value = "South Africa"
        viewModel.onSearchQueryChanged()
        viewModel.countries.value?.let {
            assertEquals(1, it.size)
        }
    }

    @Test
    fun `try again should clear error status`(){
        viewModel.showError.value = true
        viewModel.onTryAgain()
        viewModel.showError.value?.let {
            assertFalse(it)
        }
    }
}