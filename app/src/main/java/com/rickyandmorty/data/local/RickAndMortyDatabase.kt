package com.rickyandmorty.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rickyandmorty.data.local.entity.FavoriteCharacter
import com.rickyandmorty.domain.CharactersDomain

@Database(entities = [CharactersDomain::class], version = 1)
abstract class RickAndMortyDatabase : RoomDatabase() {

    abstract val rickMortyDao: RickAndMortyDao
}