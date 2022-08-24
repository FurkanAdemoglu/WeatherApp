package com.example.kafeinweatherapp.model.entity


import com.google.gson.annotations.SerializedName

data class TimeZone(
    @SerializedName("Code")
    val code: String,
    @SerializedName("GmtOffset")
    val gmtOffset: Int,
    @SerializedName("Name")
    val name: String,
    @SerializedName("NextOffsetChange")
    val nextOffsetChange: Any,
    @SerializedName("IsDaylightSaving")
    val isDaylightSaving: Boolean
)