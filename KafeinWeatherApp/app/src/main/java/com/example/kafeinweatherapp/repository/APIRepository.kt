package com.example.kafeinweatherapp.repository

import com.example.kafeinweatherapp.model.remote.RemoteDataSource
import com.example.kafeinweatherapp.utils.performNetworkOperation
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private var remoteDataSource: RemoteDataSource,
) {
    fun getLocationData(location:String) = performNetworkOperation {
        remoteDataSource.getLocationData(location)
    }



}