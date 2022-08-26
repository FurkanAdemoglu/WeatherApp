package com.example.kafeinweatherapp.ui.search

import androidx.lifecycle.*
import com.example.kafeinweatherapp.model.entity.fivedayresponse.WeatherFiveDayResponse
import com.example.kafeinweatherapp.model.entity.search.SearchResponse
import com.example.kafeinweatherapp.model.entity.searchedwords.Word
import com.example.kafeinweatherapp.model.entity.twelvehourresponse.WeatherTwelveHourResponse
import com.example.kafeinweatherapp.repository.ApiRepository
import com.example.kafeinweatherapp.ui.home.HomeViewModel
import com.example.kafeinweatherapp.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val apiRepository: ApiRepository,
) : ViewModel() {

    val liveData=MutableLiveData<State>()

    fun search(search:String) {
        viewModelScope.launch {
            apiRepository.insertWord(word = search)
            apiRepository.search(search).collect{response->
                when(response){
                    is DataState.Error->{
                        liveData.value = State.OnError(response.apiError?:"")
                    }
                    is DataState.Success->{
                        liveData.value = State.OnSearchResponseSuccess(response.data)
                    }
                    is DataState.Loading->{

                    }
                }
            }
        }

    }

    val allWords: LiveData<List<Word>> = apiRepository.allWords.asLiveData()


    sealed class State{
        data class OnError(val errorMessage:String):State()
        data class OnSearchResponseSuccess(val data:SearchResponse):State()

    }

}