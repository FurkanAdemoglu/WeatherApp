package com.example.kafeinweatherapp.model.remote

import com.example.kafeinweatherapp.utils.BaseDataSource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) : BaseDataSource() {

    suspend fun getLocationData(location:String) = getResult {
        apiService.getLocationData(location = location)
    }
}