package com.example.kafeinweatherapp.model.entity.geopointresponse


import com.google.gson.annotations.SerializedName

data class Metric(
    @SerializedName("Unit")
    val unit: String,
    @SerializedName("UnitType")
    val unitType: Int,
    @SerializedName("Value")
    val value: Int
)