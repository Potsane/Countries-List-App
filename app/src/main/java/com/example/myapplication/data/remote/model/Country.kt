package com.example.myapplication.data.remote.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Country(
    @PrimaryKey
    var commonName: String = "",
    var currencies: MutableList<String> = mutableListOf(),
    var capitalCities: MutableList<String> = mutableListOf(),
    var languages: MutableList<String> = mutableListOf(),
    var area: Double? = 0.0,
    var population: Long? = 0L,
    var timezones: MutableList<String> = mutableListOf(),
    var continents: MutableList<String> = mutableListOf(),
    var flagImageUrl: String? = "",
) : Parcelable
