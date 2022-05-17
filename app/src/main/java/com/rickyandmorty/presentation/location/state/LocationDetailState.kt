package com.rickyandmorty.presentation.location.state

import com.rickyandmorty.domain.CharactersDomain
import com.rickyandmorty.domain.model.LocationByIdDomain

data class LocationDetailState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val locationInfo: LocationByIdDomain? = null,
    val locationID: Int = 0,
    val characterList: List<CharactersDomain>? = null
)