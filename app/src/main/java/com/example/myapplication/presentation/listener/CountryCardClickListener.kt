package com.example.myapplication.presentation.listener

import com.example.myapplication.data.model.Country

interface CountryCardClickListener {
    fun onCardClick(country: Country)
}