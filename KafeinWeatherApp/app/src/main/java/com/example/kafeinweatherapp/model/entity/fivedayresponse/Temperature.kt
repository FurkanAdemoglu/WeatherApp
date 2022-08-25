package com.example.kafeinweatherapp.model.entity.fivedayresponse


import com.google.gson.annotations.SerializedName

data class Temperature(
    @SerializedName("Maximum")
    val maximum: Maximum,
    @SerializedName("Minimum")
    val minimum: Minimum
)