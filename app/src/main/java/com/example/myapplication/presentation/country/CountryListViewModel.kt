package com.example.myapplication.presentation.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.model.Country
import com.example.myapplication.domain.model.Result
import com.example.myapplication.domain.repository.CountryRepository
import com.example.myapplication.presentation.base.CountriesAppBaseViewModel
import com.example.myapplication.presentation.listener.CountryCardClickListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val repository: CountryRepository
) : CountriesAppBaseViewModel(), CountryCardClickListener {

    var isNetworkAvailable = true
    val searchQuery = MutableLiveData("")
    var showError = MutableLiveData(false)
    private var unfilteredList = mutableListOf<Country>()
    private val _countries = MutableLiveData<MutableList<Country>>()
    val countries: LiveData<MutableList<Country>> = _countries

    fun getCountryList(
        isNetworkAvailable: Boolean,
        forceRefresh: Boolean = false
    ) {
        if (forceRefresh && !isNetworkAvailable) {
            showError.value = true
            return
        }
        withProgress {
            repository.getCountryList(
                isNetworkAvailable = isNetworkAvailable,
                forceRefresh = forceRefresh,
                onSuccess = { handleCountriesListResult(it) },
                onError = { showError.value = true }
            )
        }
    }

    private fun handleCountriesListResult(result: Result<List<Country>>) {
        val countries = (result as Result.Success).data
        unfilteredList = countries.sortedBy { it.commonName }.toMutableList()
        finalizeList(unfilteredList)
    }

    private fun finalizeList(countries: MutableList<Country>) {
        searchQuery.value?.takeIf { it.isNotEmpty() }?.let { query ->
            _countries.value = countries
                .filter { it.commonName.contains(query) }
                .toMutableList()
        } ?: run { _countries.value = countries }
    }

    fun onRefresh(onRefreshComplete: () -> Unit) {
        searchQuery.value = ""
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

    override fun onCardClick(country: Country) {
        navigate(CountryListFragmentDirections.listToDetail(country))
    }
}