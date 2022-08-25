package com.example.kafeinweatherapp.ui.splash

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.kafeinweatherapp.model.entity.geopointresponse.GeoPositionResponse
import com.example.kafeinweatherapp.repository.ApiRepository
import com.example.kafeinweatherapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val apiRepository: ApiRepository
) : ViewModel() {
    var location: Location?=null

    fun getLocationData(location:String): LiveData<Resource<GeoPositionResponse>> = apiRepository.getLocationData(location)

}

