package com.example.myapplication.presentation.ui

import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentCountryDetailsBinding
import com.example.myapplication.presentation.base.CountriesAppBaseFragment

class CountryDetailsFragment :
    CountriesAppBaseFragment<CountryDetailsViewModel, FragmentCountryDetailsBinding>() {

    private val arguments: CountryDetailsFragmentArgs by navArgs()

    override fun onResume() {
        super.onResume()
        val country = arguments.country
        binding.country = country
    }

    override fun createViewModel() = ViewModelProvider(this)[CountryDetailsViewModel::class.java]

    override fun getLayoutId() = R.layout.fragment_country_details
}