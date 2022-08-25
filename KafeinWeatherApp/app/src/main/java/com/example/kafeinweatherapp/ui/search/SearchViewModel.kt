package com.example.kafeinweatherapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.kafeinweatherapp.model.entity.fivedayresponse.WeatherFiveDayResponse
import com.example.kafeinweatherapp.model.entity.search.SearchResponse
import com.example.kafeinweatherapp.model.entity.twelvehourresponse.WeatherTwelveHourResponse
import com.example.kafeinweatherapp.repository.ApiRepository
import com.example.kafeinweatherapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    val apiRepository: ApiRepository
) : ViewModel() {

   private val _searchResponse =MutableLiveData<SearchResponse>()
    val searchResponse:LiveData<SearchResponse> = _searchResponse

    fun search(search:String): LiveData<Resource<SearchResponse>> = apiRepository.search(search)



}