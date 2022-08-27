package com.example.kafeinweatherapp.ui.splash

import android.location.Location
import android.provider.ContactsContract
import androidx.lifecycle.*
import com.example.kafeinweatherapp.model.entity.geopointresponse.GeoPositionResponse
import com.example.kafeinweatherapp.repository.ApiRepository
import com.example.kafeinweatherapp.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val apiRepository: ApiRepository
) : ViewModel() {
    var location: Location?=null

    val liveData = MutableLiveData<State>()


     fun getLocationData(location:String){
        viewModelScope.launch {
            apiRepository.getLocationData(location).collect{response->
            when(response){
                is DataState.Error->{
                liveData.value = State.OnError(response.apiError?:"")
                }
                is DataState.Success->{
                    liveData.value = State.OnSuccess(response.data)
                }
                is DataState.Loading->{

                }
            }
            }
        }

    }

    sealed class State{
        data class OnError(val errorMessage:String):State()
        data class OnSuccess(val data:GeoPositionResponse ):State()
    }

}

