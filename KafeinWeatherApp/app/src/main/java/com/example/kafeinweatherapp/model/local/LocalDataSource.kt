package com.example.kafeinweatherapp.model.local

import javax.inject.Inject

class LocalDataSource @Inject constructor(private val sharedPrefManager: SharedPrefManager) {
    fun saveKey(key: String) {
        sharedPrefManager.saveKey(key)
    }

    fun getKey():String?{
        return sharedPrefManager.getKey()
    }

    fun saveCity(city:String){
        sharedPrefManager.saveCity(city)
    }

    fun getCity():String?{
        return sharedPrefManager.getCity()
    }
}