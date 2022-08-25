package com.example.kafeinweatherapp.model.entity.twelvehourresponse


import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("Direction")
    val direction: Direction,
    @SerializedName("Speed")
    val speed: Speed
)