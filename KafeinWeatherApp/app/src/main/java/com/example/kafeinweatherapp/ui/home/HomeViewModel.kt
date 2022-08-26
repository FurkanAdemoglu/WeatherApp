package com.example.kafeinweatherapp.ui.home

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.kafeinweatherapp.model.entity.fivedayresponse.WeatherFiveDayResponse
import com.example.kafeinweatherapp.model.entity.geopointresponse.GeoPositionResponse
import com.example.kafeinweatherapp.model.entity.twelvehourresponse.WeatherTwelveHourResponse
import com.example.kafeinweatherapp.model.local.LocalDataSource
import com.example.kafeinweatherapp.repository.ApiRepository
import com.example.kafeinweatherapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(
    val apiRepository: ApiRepository,
    val localDataSource: LocalDataSource
) : ViewModel() {


    fun getWeather5DaysForecast(): LiveData<Resource<WeatherFiveDayResponse>> = apiRepository.getWeather5DaysForecast(localDataSource.getKey()?:"")

    fun getWeather12HourlyForecast(): LiveData<Resource<WeatherTwelveHourResponse>> = apiRepository.getWeather12HourlyForecast(localDataSource.getKey()?:"")

    fun getWeather5DaysForecastDetail(key:String): LiveData<Resource<WeatherFiveDayResponse>> = apiRepository.getWeather5DaysForecast(key)

    fun getWeather12HourlyForecastDetail(key:String): LiveData<Resource<WeatherTwelveHourResponse>> = apiRepository.getWeather12HourlyForecast(key)
}
