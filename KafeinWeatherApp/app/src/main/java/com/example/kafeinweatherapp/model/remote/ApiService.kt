package com.example.kafeinweatherapp.model.remote

import com.example.kafeinweatherapp.model.entity.fivedayresponse.WeatherFiveDayResponse
import com.example.kafeinweatherapp.model.entity.geopointresponse.GeoPositionResponse
import com.example.kafeinweatherapp.model.entity.search.SearchResponse
import com.example.kafeinweatherapp.model.entity.twelvehourresponse.WeatherTwelveHourResponse
import com.example.kafeinweatherapp.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("locations/v1/cities/geoposition/search")
    suspend fun getLocationData(@Query("apikey") apiKey: String = API_KEY, @Query("q") location: String): Response<GeoPositionResponse>

    @GET("forecasts/v1/hourly/12hour/{key}")
    suspend fun getWeather12HourlyForecast(@Path("key") key: String, @Query("apikey") apikey: String = API_KEY, @Query("details") details: String = "true", @Query("metric") metric: String = "true"): Response<WeatherTwelveHourResponse>

    @GET("forecasts/v1/daily/5day/{key}")
    suspend fun getWeather5DaysForecast(@Path("key") key: String, @Query("apikey") apikey: String = API_KEY, @Query("details") details: String = "false", @Query("metric") metric: String = "false"): Response<WeatherFiveDayResponse>

    @GET("/locations/v1/cities/autocomplete")
    suspend fun search(@Query("apikey") apiKey: String = API_KEY, @Query("q") search: String): Response<SearchResponse>
}

