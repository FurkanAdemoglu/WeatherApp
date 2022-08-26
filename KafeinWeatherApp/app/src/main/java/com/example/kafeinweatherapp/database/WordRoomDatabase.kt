package com.example.kafeinweatherapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kafeinweatherapp.model.entity.searchedwords.Word

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
public abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao


}