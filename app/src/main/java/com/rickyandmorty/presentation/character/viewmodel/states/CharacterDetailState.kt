package com.rickyandmorty.presentation.character.viewmodel.states

import com.rickyandmorty.data.remote.dto.character.CharacterData
import com.rickyandmorty.domain.model.EpisodeDomain

data class CharacterDetailState(
    val character: CharacterData? = null,
    val characterIdFromCharacterListFragment: Int = 1,
    val navigateArgLocationId: Int? = null,
    val episodeList: List<EpisodeDomain>? = null

)