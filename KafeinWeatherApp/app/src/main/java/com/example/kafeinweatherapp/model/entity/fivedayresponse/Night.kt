package com.example.kafeinweatherapp.model.entity.fivedayresponse


import com.google.gson.annotations.SerializedName

data class Night(
    @SerializedName("HasPrecipitation")
    val hasPrecipitation: Boolean,
    @SerializedName("PrecipitationIntensity")
    val precipitationIntensity: String,
    @SerializedName("PrecipitationType")
    val precipitationType: String,
    @SerializedName("Icon")
    val icon: Int,
    @SerializedName("IconPhrase")
    val iconPhrase: String
)