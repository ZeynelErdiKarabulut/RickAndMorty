package com.rickyandmorty.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rickyandmorty.domain.CharactersDomain


data class FavoriteCharacter(
   val id: Int,
    val name: String,
    val status: String,
    val gender: String,
    val image: String
)


