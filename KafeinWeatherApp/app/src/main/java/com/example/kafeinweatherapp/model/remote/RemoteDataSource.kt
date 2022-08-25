package com.example.kafeinweatherapp.model.remote

import com.example.kafeinweatherapp.utils.BaseDataSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : BaseDataSource() {

    suspend fun getLocationData(location:String) = getResult {
        apiService.getLocationData(location = location)
    }

    suspend fun getWeather12HourlyForecast(key:String) = getResult {
        apiService.getWeather12HourlyForecast(key = key)
    }
    suspend fun getWeather5DaysForecast(key: String) = getResult {
        apiService.getWeather5DaysForecast(key = key)
    }
    suspend fun search(search: String) = getResult {
        apiService.search(search = search)
    }

}