package com.example.kafeinweatherapp.model.entity.twelvehourresponse


import com.google.gson.annotations.SerializedName

data class RealFeelTemperatureShade(
    @SerializedName("Phrase")
    val phrase: String,
    @SerializedName("Unit")
    val unit: String,
    @SerializedName("UnitType")
    val unitType: Int,
    @SerializedName("Value")
    val value: Double
)