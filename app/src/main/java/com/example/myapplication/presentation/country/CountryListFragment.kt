package com.example.myapplication.presentation.country

import android.net.ConnectivityManager
import android.net.Network
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.data.model.Country
import com.example.myapplication.databinding.FragmentCountryListBinding
import com.example.myapplication.presentation.base.CountriesAppBaseFragment
import com.example.myapplication.presentation.country.adapter.CountryListAdapter
import com.example.myapplication.util.network.NetworkMonitor
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryListFragment :
    CountriesAppBaseFragment<CountryListViewModel, FragmentCountryListBinding>() {

    private var countriesListAdapter: CountryListAdapter? = null
    private var networkMonitor: NetworkMonitor? = null
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            viewModel.isNetworkAvailable = true
        }

        override fun onLost(network: Network) {
            viewModel.isNetworkAvailable = false
        }
    }

    override fun onResume() {
        super.onResume()
        networkMonitor = NetworkMonitor(requireContext())
        networkMonitor?.registerNetworkCallback(networkCallback)
        viewModel.getCountryList(networkMonitor?.isNetworkAvailable() ?: true)
        viewModel.countries.observe(viewLifecycleOwner, Observer(::setupListview))
        setupSwipeToRefresh()
    }

    override fun onPause() {
        super.onPause()
        networkMonitor?.unregisterNetworkCallback(networkCallback)
        countriesListAdapter = null
        networkMonitor = null
    }

    override fun createViewModel() = ViewModelProvider(this)[CountryListViewModel::class.java]

    override fun getLayoutId() = R.layout.fragment_country_list

    private fun setupSwipeToRefresh() {
        binding.refreshView.apply {
            setOnRefreshListener {
                viewModel.onRefresh { isRefreshing = false }
                Snackbar.make(binding.root, "Refreshing data...", Snackbar.LENGTH_SHORT).show()
            }
            setProgressViewEndTarget(false, 0)
        }
    }

    private fun setupListview(countries: MutableList<Country>) {
        countriesListAdapter?.let {
            if (countries.isNotEmpty()) it.updateItems(countries)
        } ?: run {
            countriesListAdapter = CountryListAdapter(countries, viewModel)
            binding.countryListView.apply { adapter = countriesListAdapter }
        }
    }
}