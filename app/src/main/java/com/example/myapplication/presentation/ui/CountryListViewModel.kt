package com.example.myapplication.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.remote.model.Country
import com.example.myapplication.domain.repository.model.Result
import com.example.myapplication.domain.repository.CountryRepository
import com.example.myapplication.presentation.base.CountriesAppBaseViewModel
import com.example.myapplication.presentation.ui.listener.CountryCardClickListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val repository: CountryRepository
) : CountriesAppBaseViewModel(), CountryCardClickListener {

    var isNetworkAvailable = true
    val searchQuery = MutableLiveData("")
    var showError = MutableLiveData(false)
    private var unfilteredList = listOf<Country>()
    private val _countries = MutableLiveData<MutableList<Country>>()
    val countries: LiveData<MutableList<Country>> = _countries

    fun getCountryList(
        isNetworkAvailable: Boolean,
        forceRefresh: Boolean = false
    ) {
        searchQuery.value = ""
        when {
            forceRefresh && !isNetworkAvailable -> {
                showError.value = true
                return
            }
            unfilteredList.isNotEmpty() && !forceRefresh -> initializeList(unfilteredList)
            else -> {
                withProgress {
                    repository.getCountryList(
                        isNetworkAvailable = isNetworkAvailable,
                        forceRefresh = forceRefresh,
                        onSuccess = { handleCountriesListResult(it) },
                        onError = { showError.value = true }
                    )
                }
            }
        }
    }

    fun onRefresh(onRefreshComplete: () -> Unit) {
        getCountryList(forceRefresh = true, isNetworkAvailable = isNetworkAvailable)
        onRefreshComplete()
    }

    fun onSearchQueryChanged() = searchQuery.value?.let { query ->
        val filteredList = unfilteredList.filter {
            it.commonName.contains(query, true)
        }
        _countries.value = filteredList.toMutableList()
    }

    fun onTryAgain() {
        showError.value = false
        getCountryList(isNetworkAvailable)
    }

    private fun handleCountriesListResult(result: Result<List<Country>>) {
        val countries = (result as Result.Success).data
        unfilteredList = countries.sortedBy { it.commonName }
        initializeList(unfilteredList)
    }

    private fun initializeList(countries: List<Country>) {
        _countries.value = countries.toMutableList()
    }

    override fun onCardClick(country: Country) = navigate(CountryListFragmentDirections.listToDetail(country))
}