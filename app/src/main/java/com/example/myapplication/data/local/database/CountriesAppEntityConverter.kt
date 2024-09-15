package com.example.myapplication.data.local.database

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CountriesAppEntityConverter {

    @TypeConverter
    fun fromStringList(value : MutableList<String>) = Json.encodeToString(value)

    @TypeConverter
    fun toStringList(value: String) = Json.decodeFromString<MutableList<String>>(value)

    @TypeConverter
    fun fromDoubleList(value : MutableList<Double>) = Json.encodeToString(value)

    @TypeConverter
    fun toDoubleList(value: String) = Json.decodeFromString<MutableList<Double>>(value)

}