package com.example.kafeinweatherapp.model.entity.geopointresponse


import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("EnglishName")
    val englishName: String,
    @SerializedName("LocalizedName")
    val localizedName: String,
    @SerializedName("ID")
    val id: String
)