package com.example.myapplication.data.remote.adapter

import android.annotation.SuppressLint
import com.example.myapplication.data.remote.model.CountriesResponse
import com.example.myapplication.data.remote.model.Country
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
                        reader.beginObject()
                        while (reader.hasNext()) {
                            val nameKey = reader.nextName()
                            when (nameKey) {
                                "common" -> country.commonName = reader.nextString()
                                else -> reader.skipValue()
                            }
                        }
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

                    "area" -> country.area = reader.nextDouble()
                    "population" -> country.population = reader.nextLong()
                    "currencies" -> {
                        reader.beginObject()
                        while (reader.hasNext()) {
                            reader.nextName()
                            reader.beginObject()
                            while (reader.hasNext()) {
                                val currencyKey = reader.nextName()
                                when (currencyKey) {
                                    "name" -> country.currencies.add(reader.nextString())
                                    else -> reader.skipValue()
                                }
                            }
                            reader.endObject()
                        }
                        reader.endObject()
                    }

                    "capital" -> {
                        reader.beginArray()
                        while (reader.hasNext()) {
                            country.capitalCities.add(reader.nextString())
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
                            val flagKey = reader.nextName()
                            when (flagKey) {
                                "png" -> country.flagImageUrl = reader.nextString()
                                else -> reader.skipValue()
                            }
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