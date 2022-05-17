package com.rickyandmorty.data.remote.dto.character

import com.rickyandmorty.data.remote.dto.Info


data class CharactersDto(
    val info: Info,
    val results: List<CharacterData>
)

