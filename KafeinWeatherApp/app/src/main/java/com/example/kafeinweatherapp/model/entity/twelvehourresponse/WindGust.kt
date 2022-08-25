package com.example.kafeinweatherapp.model.entity.twelvehourresponse


import com.google.gson.annotations.SerializedName

data class WindGust(
    @SerializedName("Speed")
    val speed: Speed
)