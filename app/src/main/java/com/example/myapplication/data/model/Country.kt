package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Country(
    @PrimaryKey
    var commonName: String = "",
    var officialName: String? = "",
    var independent: Boolean? = false,
    var isUnMember: Boolean? = false,
    var currencies: MutableList<String> = mutableListOf(),
    var capitalCities: MutableList<String> = mutableListOf(),
    var region: String? = "",
    var subregion: String? = "",
    var languages: MutableList<String> = mutableListOf(),
    var latlng: MutableList<Double> = mutableListOf(),
    var isLandlocked: Boolean? = false,
    var area: Double? = 0.0,
    var population: Long? = 0L,
    var timezones: MutableList<String> = mutableListOf(),
    var continents: MutableList<String> = mutableListOf(),
    var flagImageUrl: String? = "",
    var coatOfArms: String? = "",
    var startOfWeek: String? = "",
)
