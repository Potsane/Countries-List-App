package com.example.myapplication.data.remote.adapter

import android.annotation.SuppressLint
import com.example.myapplication.data.model.CountriesResponse
import com.example.myapplication.data.model.Country
import com.example.myapplication.data.model.CountryCurrency
import com.example.myapplication.data.model.CountryName
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

class CountryDataJasonAdapter : JsonAdapter<CountriesResponse>() {
    @FromJson
    override fun fromJson(p0: JsonReader): CountriesResponse {
        return CountriesResponse(getCountries(p0))
    }

    @ToJson
    override fun toJson(p0: JsonWriter, p1: CountriesResponse?) {
    }

    @SuppressLint("CheckResult")
    private fun getCountries(reader: JsonReader): MutableList<Country> {
        val countries = mutableListOf<Country>()

        reader.beginArray()
        while (reader.hasNext()) {
            reader.beginObject()
            val country = Country()
            while (reader.hasNext()) {

                val key = reader.nextName()
                when (key) {
                    "name" -> {
                        val countryName = CountryName()
                        reader.beginObject()
                        while (reader.hasNext()) {
                            val nameKey = reader.nextName()
                            when (nameKey) {
                                "common" -> countryName.common = reader.nextString()
                                "official" -> countryName.official = reader.nextString()
                                else -> reader.skipValue()
                            }
                        }
                        country.name = countryName
                        reader.endObject()
                    }
                    "languages" -> {
                        reader.beginObject()
                        while (reader.hasNext()) {
                            reader.nextName()
                            country.languages.add(reader.nextString())
                        }
                        reader.endObject()
                    }
                    "independent" -> country.independent = reader.nextBoolean()
                    "unMember" -> country.isUnMember = reader.nextBoolean()
                    "landlocked" -> country.isLandlocked = reader.nextBoolean()
                    "area" -> country.area = reader.nextDouble()
                    "population" -> country.population = reader.nextLong()
                    "startOfWeek" -> country.startOfWeek = reader.nextString()
                    "region" -> country.region = reader.nextString()
                    "currencies" -> {
                        val countryCurrency = CountryCurrency()
                        reader.beginObject()
                        while (reader.hasNext()) {
                            reader.nextName()
                            reader.beginObject()
                            while (reader.hasNext()) {
                                val currencyKey = reader.nextName()
                                when (currencyKey) {
                                    "name" -> countryCurrency.name = reader.nextString()
                                    "symbol" -> countryCurrency.symbol = reader.nextString()
                                }
                            }
                            reader.endObject()
                        }
                        country.currencies.add(countryCurrency)
                        reader.endObject()
                    }
                    "capital" -> {
                        reader.beginArray()
                        while (reader.hasNext()) {
                            country.capitalCities.add(reader.nextString())
                        }
                        reader.endArray()
                    }

                    "latlng" -> {
                        reader.beginArray()
                        while (reader.hasNext()) {
                            country.latlng.add(reader.nextDouble())
                        }
                        reader.endArray()
                    }

                    "timezones" -> {
                        reader.beginArray()
                        while (reader.hasNext()) {
                            country.timezones.add(reader.nextString())
                        }
                        reader.endArray()
                    }
                    "continents" -> {
                        reader.beginArray()
                        while (reader.hasNext()) {
                            country.continents.add(reader.nextString())
                        }
                        reader.endArray()
                    }
                    "flags" -> {
                        reader.beginObject()
                        while (reader.hasNext()) {
                            val flag = Pair(reader.nextName(), reader.nextString())
                            country.flags.add(flag)
                        }
                        reader.endObject()
                    }

                    else -> reader.skipValue()
                }

            }
            countries.add(country)
            reader.endObject()
        }
        reader.endArray()
        return countries
    }
}