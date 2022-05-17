package com.rickyandmorty.presentation.favorite

import com.rickyandmorty.domain.CharactersDomain

data class FavoriteState(
    val characterList: List<CharactersDomain> = emptyList(),
    val isError: Boolean = false
)