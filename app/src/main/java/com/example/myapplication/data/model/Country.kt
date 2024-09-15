package com.example.myapplication.data.model

data class Country(
    var name: CountryName? = CountryName(),
    var independent: Boolean? = false,
    var isUnMember: Boolean? = false,
    var currencies: MutableList<CountryCurrency> = mutableListOf(),
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
    var flags: MutableList<Pair<String, String>> = mutableListOf(),
    var coatOfArms: MutableList<Pair<String, String>> = mutableListOf(),
    var startOfWeek: String? = "",
)

data class CountryCurrency(
    var name: String? = "",
    var symbol: String? = "",
)

data class CountryName(
    var common: String? = "",
    var official: String? = ""
)
