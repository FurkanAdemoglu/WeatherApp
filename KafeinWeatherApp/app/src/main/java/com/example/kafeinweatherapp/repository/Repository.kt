package com.example.kafeinweatherapp.repository

import com.example.kafeinweatherapp.database.WordDao
import com.example.kafeinweatherapp.model.entity.fivedayresponse.WeatherFiveDayResponse
import com.example.kafeinweatherapp.model.entity.geopointresponse.GeoPositionResponse
import com.example.kafeinweatherapp.model.entity.search.SearchResponse
import com.example.kafeinweatherapp.model.entity.searchedwords.Word
import com.example.kafeinweatherapp.model.entity.twelvehourresponse.WeatherTwelveHourResponse
import com.example.kafeinweatherapp.model.remote.RemoteDataSource
import com.example.kafeinweatherapp.ui.home.adapters.WeatherHourlyForecastAdapter
import com.example.kafeinweatherapp.utils.DataState

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private var remoteDataSource: RemoteDataSource,
    private var wordDao: WordDao
) {
    suspend fun getLocationData(location:String) :Flow<DataState<GeoPositionResponse>> {
        return remoteDataSource.getLocationData(location)
    }

    suspend fun getWeather5DaysForecast(key:String) :Flow<DataState<WeatherFiveDayResponse>> {
      return   remoteDataSource.getWeather5DaysForecast(key = key)
    }

    suspend fun getWeather12HourlyForecast(key:String):Flow<DataState<WeatherTwelveHourResponse>> {
      return   remoteDataSource.getWeather12HourlyForecast(key = key)
    }

     suspend fun search(search:String):Flow<DataState<SearchResponse>> {
       return  remoteDataSource.search(search = search)
    }

    suspend fun insertWord(word:String){
        wordDao.insert(Word(word = word))
    }

    val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWords()



}