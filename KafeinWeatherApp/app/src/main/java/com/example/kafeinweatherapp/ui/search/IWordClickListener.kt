package com.example.kafeinweatherapp.ui.search

import com.example.kafeinweatherapp.model.entity.search.SearchResponseItem
import com.example.kafeinweatherapp.model.entity.searchedwords.Word

interface IWordClickListener {
    fun onClick(name: Word)
}