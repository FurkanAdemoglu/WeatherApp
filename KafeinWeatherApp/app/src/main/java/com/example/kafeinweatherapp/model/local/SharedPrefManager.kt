package com.example.kafeinweatherapp.model.local

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context:Context) {
    companion object {
        const val KEY = "com.example.universityapp.model.local.KEY"
        const val CITY = "com.example.universityapp.model.local.CITY"
        const val LATITUDE = "com.example.universityapp.model.local.LATITUDE"
        const val LONGTITUDE = "com.example.universityapp.model.local.LONGTITUDE"

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

    fun saveLatitude(latitude:String){
        sharedPreferences.edit().putString(LATITUDE, latitude?:"41").apply()
    }
    fun getLatitude(): String? {
        return sharedPreferences.getString(LATITUDE, "41")
    }
    fun saveLongtitude(latitude:String){
        sharedPreferences.edit().putString(LONGTITUDE, latitude?:"28").apply()
    }
    fun getLongtitude(): String? {
        return sharedPreferences.getString(LONGTITUDE, "28")
    }



}