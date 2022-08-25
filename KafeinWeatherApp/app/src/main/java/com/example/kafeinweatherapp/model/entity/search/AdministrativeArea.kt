package com.example.kafeinweatherapp.model.entity.search


import com.google.gson.annotations.SerializedName

data class AdministrativeArea(
    @SerializedName("LocalizedName")
    val localizedName: String,
    @SerializedName("ID")
    val id: String
)