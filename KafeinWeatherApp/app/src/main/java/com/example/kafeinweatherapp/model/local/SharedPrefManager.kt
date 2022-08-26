package com.example.kafeinweatherapp.model.local

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context:Context) {
    companion object {
        const val KEY = "com.example.universityapp.model.local.KEY"
        const val CITY = "com.example.universityapp.model.local.CITY"

    }
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("sharedPreferencesUtil", Context.MODE_PRIVATE)

    fun saveKey(key:String){
        sharedPreferences.edit().putString(KEY, key).apply()
    }
    fun getKey(): String? {
        return sharedPreferences.getString(KEY, "")
    }

    fun saveCity(city:String){
        sharedPreferences.edit().putString(CITY, city).apply()
    }
    fun getCity(): String? {
        return sharedPreferences.getString(CITY, "")
    }

}