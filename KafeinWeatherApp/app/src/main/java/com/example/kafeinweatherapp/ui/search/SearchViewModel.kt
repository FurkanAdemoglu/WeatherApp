package com.example.kafeinweatherapp.ui.search

import androidx.lifecycle.*
import com.example.kafeinweatherapp.model.entity.fivedayresponse.WeatherFiveDayResponse
import com.example.kafeinweatherapp.model.entity.search.SearchResponse
import com.example.kafeinweatherapp.model.entity.searchedwords.Word
import com.example.kafeinweatherapp.model.entity.twelvehourresponse.WeatherTwelveHourResponse
import com.example.kafeinweatherapp.repository.ApiRepository
import com.example.kafeinweatherapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val apiRepository: ApiRepository,
) : ViewModel() {

   private val _searchResponse =MutableLiveData<SearchResponse>()
    val searchResponse:LiveData<SearchResponse> = _searchResponse

    fun search(search:String): LiveData<Resource<SearchResponse>> {
        viewModelScope.launch {
            apiRepository.insertWord(word = search)
        }
       return apiRepository.search(search)
    }

    val allWords: LiveData<List<Word>> = apiRepository.allWords.asLiveData()




}