package com.example.kafeinweatherapp.ui.search

import com.example.kafeinweatherapp.model.entity.search.SearchResponseItem

interface ICityClickListener {
    fun onClick(name: SearchResponseItem)
}