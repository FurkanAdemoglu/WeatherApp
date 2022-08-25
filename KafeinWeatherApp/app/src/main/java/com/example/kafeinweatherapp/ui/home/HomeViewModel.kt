package com.example.kafeinweatherapp.ui.home

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.kafeinweatherapp.model.entity.fivedayresponse.WeatherFiveDayResponse
import com.example.kafeinweatherapp.model.entity.geopointresponse.GeoPositionResponse
import com.example.kafeinweatherapp.model.entity.twelvehourresponse.WeatherTwelveHourResponse
import com.example.kafeinweatherapp.repository.ApiRepository
import com.example.kafeinweatherapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val apiRepository: ApiRepository
) : ViewModel() {


    fun getWeather5DaysForecast(key:String): LiveData<Resource<WeatherFiveDayResponse>> = apiRepository.getWeather5DaysForecast(key = key)

    fun getWeather12HourlyForecast(key:String): LiveData<Resource<WeatherTwelveHourResponse>> = apiRepository.getWeather12HourlyForecast(key = key)

}
