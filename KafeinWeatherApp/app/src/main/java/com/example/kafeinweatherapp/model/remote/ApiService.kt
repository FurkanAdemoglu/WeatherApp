package com.example.kafeinweatherapp.model.remote

import com.example.kafeinweatherapp.model.entity.GeoPositionResponse
import com.example.kafeinweatherapp.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("locations/v1/cities/geoposition/search")
    suspend fun getLocationData(@Query("apikey") apiKey: String = API_KEY, @Query("q") location: String): Response<GeoPositionResponse>
}