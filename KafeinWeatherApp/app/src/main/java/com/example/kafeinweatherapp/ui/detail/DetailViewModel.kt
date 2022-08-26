package com.example.kafeinweatherapp.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kafeinweatherapp.model.entity.fivedayresponse.WeatherFiveDayResponse
import com.example.kafeinweatherapp.model.entity.twelvehourresponse.WeatherTwelveHourResponse
import com.example.kafeinweatherapp.model.local.LocalDataSource
import com.example.kafeinweatherapp.repository.ApiRepository
import com.example.kafeinweatherapp.ui.home.HomeViewModel
import com.example.kafeinweatherapp.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val apiRepository: ApiRepository,
    val localDataSource: LocalDataSource
) : ViewModel() {

    val liveData = MutableLiveData<State>()

    fun getWeather5DaysForecastDetail(key:String) {
        viewModelScope.launch {
            apiRepository.getWeather5DaysForecast(key).collect{response->
                when(response){
                    is DataState.Error->{
                        liveData.value = State.OnError(response.apiError?:"")
                    }
                    is DataState.Success->{
                        liveData.value = State.OnWeatherFiveDayDetailSuccess(response.data)
                    }
                    is DataState.Loading->{

                    }
                }
            }
        }

    }

    fun getWeather12HourlyForecastDetail(key:String){
        viewModelScope.launch {
            apiRepository.getWeather12HourlyForecast(key).collect{response->
                when(response){
                    is DataState.Error->{
                        liveData.value = State.OnError(response.apiError?:"")
                    }
                    is DataState.Success->{
                        liveData.value = State.OnWeatherTwelveHourDetailSuccess(response.data)
                    }
                    is DataState.Loading->{

                    }
                }
            }
        }

    }

    sealed class State{
        data class OnError(val errorMessage:String):State()
        data class OnWeatherFiveDayDetailSuccess(val data: WeatherFiveDayResponse):State()
        data class OnWeatherTwelveHourDetailSuccess(val data: WeatherTwelveHourResponse):State()
    }

}