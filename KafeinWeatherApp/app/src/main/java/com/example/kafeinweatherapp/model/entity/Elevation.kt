package com.example.kafeinweatherapp.model.entity


import com.google.gson.annotations.SerializedName

data class Elevation(
    @SerializedName("Metric")
    val metric: Metric,
    @SerializedName("Imperial")
    val imperial: Imperial
)