package com.example.rickandmortycoursework.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.rickandmortycoursework.domain.model.CharactersDomain

@Database(entities = [CharactersDomain::class], version = 1, exportSchema = false)
abstract class RickAndMortyDatabase : RoomDatabase() {

    abstract val rickMortyDao: RickAndMortyDao
}