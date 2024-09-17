package com.example.myapplication.presentation.country

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.data.model.Country
import com.example.myapplication.databinding.FragmentCountryListBinding
import com.example.myapplication.presentation.base.CountriesAppBaseFragment
import com.example.myapplication.presentation.country.adapter.CountryListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryListFragment :
    CountriesAppBaseFragment<CountryListViewModel, FragmentCountryListBinding>() {

    private var countriesListAdapter: CountryListAdapter? = null

    override fun onResume() {
        super.onResume()
        viewModel.getCountryList()
        viewModel.countries.observe(viewLifecycleOwner, Observer(::setupListview))
        setupSwipeToRefresh()
    }

    override fun onPause() {
        super.onPause()
        countriesListAdapter = null
    }

    override fun createViewModel() = ViewModelProvider(this)[CountryListViewModel::class.java]

    override fun getLayoutId() = R.layout.fragment_country_list

    private fun setupSwipeToRefresh() {
        binding.refreshView.apply {
            setOnRefreshListener { viewModel.onRefresh { isRefreshing = false } }
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