package com.example.kafeinweatherapp.model.entity.twelvehourresponse


import com.google.gson.annotations.SerializedName

data class WeatherTwelveHourResponseItem(
    @SerializedName("Ceiling")
    val ceiling: Ceiling,
    @SerializedName("CloudCover")
    val cloudCover: Int,
    @SerializedName("DateTime")
    val dateTime: String,
    @SerializedName("DewPoint")
    val dewPoint: DewPoint,
    @SerializedName("EpochDateTime")
    val epochDateTime: Int,
    @SerializedName("Evapotranspiration")
    val evapotranspiration: Evapotranspiration,
    @SerializedName("HasPrecipitation")
    val hasPrecipitation: Boolean,
    @SerializedName("Link")
    val link: String,
    @SerializedName("MobileLink")
    val mobileLink: String,
    @SerializedName("PrecipitationIntensity")
    val precipitationIntensity: String,
    @SerializedName("PrecipitationProbability")
    val precipitationProbability: Int,
    @SerializedName("PrecipitationType")
    val precipitationType: String,
    @SerializedName("Rain")
    val rain: Rain,
    @SerializedName("RainProbability")
    val rainProbability: Int,
    @SerializedName("RealFeelTemperature")
    val realFeelTemperature: RealFeelTemperature,
    @SerializedName("RealFeelTemperatureShade")
    val realFeelTemperatureShade: RealFeelTemperatureShade,
    @SerializedName("RelativeHumidity")
    val relativeHumidity: Int,
    @SerializedName("Snow")
    val snow: Snow,
    @SerializedName("SnowProbability")
    val snowProbability: Int,
    @SerializedName("SolarIrradiance")
    val solarIrradiance: SolarIrradiance,
    @SerializedName("Temperature")
    val temperature: Temperature,
    @SerializedName("ThunderstormProbability")
    val thunderstormProbability: Int,
    @SerializedName("TotalLiquid")
    val totalLiquid: TotalLiquid,
    @SerializedName("UVIndex")
    val uVIndex: Int,
    @SerializedName("UVIndexText")
    val uVIndexText: String,
    @SerializedName("Visibility")
    val visibility: Visibility,
    @SerializedName("WeatherIcon")
    val weatherIcon: Int,
    @SerializedName("WetBulbTemperature")
    val wetBulbTemperature: WetBulbTemperature,
    @SerializedName("Wind")
    val wind: Wind,
    @SerializedName("WindGust")
    val windGust: WindGust,
    @SerializedName("Ice")
    val ice: Ice,
    @SerializedName("IceProbability")
    val iceProbability: Int,
    @SerializedName("IconPhrase")
    val iconPhrase: String,
    @SerializedName("IndoorRelativeHumidity")
    val indoorRelativeHumidity: Int,
    @SerializedName("IsDaylight")
    val isDaylight: Boolean
)