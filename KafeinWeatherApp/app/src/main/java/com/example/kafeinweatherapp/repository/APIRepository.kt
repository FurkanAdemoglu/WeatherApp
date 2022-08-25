package com.example.kafeinweatherapp.repository

import com.example.kafeinweatherapp.model.remote.RemoteDataSource
import com.example.kafeinweatherapp.utils.performNetworkOperation
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private var remoteDataSource: RemoteDataSource,
) {
    fun getLocationData(location:String) = performNetworkOperation {
        remoteDataSource.getLocationData(location)
    }

    fun getWeather5DaysForecast(key:String) = performNetworkOperation {
        remoteDataSource.getWeather5DaysForecast(key = key)
    }

    fun getWeather12HourlyForecast(key:String) = performNetworkOperation {
        remoteDataSource.getWeather12HourlyForecast(key = key)
    }



}