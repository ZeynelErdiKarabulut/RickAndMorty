package com.rickyandmorty.data.remote.dto.character

import com.rickyandmorty.domain.model.CharacterDomain

data class CharacterDto(
    val result: CharacterData
)


fun CharacterDto.toCharacter(): CharacterDomain {
    return CharacterDomain(
        result = result
    )
}