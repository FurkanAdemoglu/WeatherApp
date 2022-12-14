package com.example.kafeinweatherapp.ui.home

import android.location.Location
import androidx.lifecycle.*
import com.example.kafeinweatherapp.model.entity.fivedayresponse.WeatherFiveDayResponse
import com.example.kafeinweatherapp.model.entity.geopointresponse.GeoPositionResponse
import com.example.kafeinweatherapp.model.entity.twelvehourresponse.WeatherTwelveHourResponse
import com.example.kafeinweatherapp.model.local.LocalDataSource
import com.example.kafeinweatherapp.repository.ApiRepository
import com.example.kafeinweatherapp.ui.splash.SplashViewModel
import com.example.kafeinweatherapp.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel  @Inject constructor(
    val apiRepository: ApiRepository,
    val localDataSource: LocalDataSource
) : ViewModel() {

    val liveData=MutableLiveData<State>()


    fun getWeather5DaysForecast(){
        viewModelScope.launch {
            apiRepository.getWeather5DaysForecast(localDataSource.getKey()?:"").collect{response->
                when(response){
                    is DataState.Error->{
                        liveData.value = State.OnError(response.apiError?:"")
                    }
                    is DataState.Success->{
                        liveData.value = State.OnWeatherFiveDaySuccess(response.data)
                    }
                    is DataState.Loading->{

                    }
                }
            }
        }
    }

    fun getWeather12HourlyForecast(){
        viewModelScope.launch {
            apiRepository.getWeather12HourlyForecast(localDataSource.getKey()?:"").collect{response->
                when(response){
                    is DataState.Error->{
                        liveData.value = State.OnError(response.apiError?:"")
                    }
                    is DataState.Success->{
                        liveData.value = State.OnWeatherTwelveHourSuccess(response.data)
                    }
                    is DataState.Loading->{

                    }
                }
            }
        }

    }



    sealed class State{
        data class OnError(val errorMessage:String):State()
        data class OnWeatherFiveDaySuccess(val data:WeatherFiveDayResponse):State()
        data class OnWeatherTwelveHourSuccess(val data:WeatherTwelveHourResponse):State()
    }
}
