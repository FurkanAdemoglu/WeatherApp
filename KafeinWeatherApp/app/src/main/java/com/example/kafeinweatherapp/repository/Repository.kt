package com.example.kafeinweatherapp.repository

import com.example.kafeinweatherapp.database.WordDao
import com.example.kafeinweatherapp.model.entity.fivedayresponse.WeatherFiveDayResponse
import com.example.kafeinweatherapp.model.entity.geopointresponse.GeoPositionResponse
import com.example.kafeinweatherapp.model.entity.search.SearchResponse
import com.example.kafeinweatherapp.model.entity.searchedwords.Word
import com.example.kafeinweatherapp.model.entity.twelvehourresponse.WeatherTwelveHourResponse
import com.example.kafeinweatherapp.model.remote.RemoteDataSource

import com.example.kafeinweatherapp.utils.performNetworkOperation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private var remoteDataSource: RemoteDataSource,
    private var wordDao: WordDao
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

     fun search(search:String)= performNetworkOperation {
        remoteDataSource.search(search = search)
    }

    suspend fun insertWord(word:String){
        wordDao.insert(Word(word = word))
    }

    val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWords()



}