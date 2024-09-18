package com.example.myapplication.presentation.ui.listener

import com.example.myapplication.data.remote.model.Country

interface CountryCardClickListener {
    fun onCardClick(country: Country)
}