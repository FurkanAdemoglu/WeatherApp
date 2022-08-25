package com.example.kafeinweatherapp.model.entity.fivedayresponse


import com.google.gson.annotations.SerializedName

data class WeatherFiveDayResponse(
    @SerializedName("DailyForecasts")
    val dailyForecasts: List<DailyForecast>,
    @SerializedName("Headline")
    val headline: Headline
)