package com.rickyandmorty.domain.repository

import androidx.paging.PagingData
import com.rickyandmorty.data.remote.dto.character.CharacterData
import com.rickyandmorty.data.remote.dto.episode.EpisodeResult
import com.rickyandmorty.data.remote.dto.location.LocationResults
import com.rickyandmorty.domain.CharactersDomain
import com.rickyandmorty.domain.model.EpisodeDomain
import com.rickyandmorty.domain.model.LocationDomain
import com.rickyandmorty.util.GenderState
import com.rickyandmorty.util.StatusState
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Path

interface RickAndMortyRepository {

    suspend fun getAllCharacters(
        status: StatusState = StatusState.NONE,
        gender: GenderState = GenderState.NONE,
        name: String = ""
    ): Flow<PagingData<CharacterData>>


    suspend fun getCharacterDetailById(characterId: Int): CharacterData

    suspend fun getAllLocation(): Flow<PagingData<LocationDomain>>

    suspend fun getLocationDetailById(locationId: Int): LocationResults

    suspend fun getAllEpisode(): Flow<PagingData<EpisodeDomain>>

    suspend fun getEpisodeById(@Path("id") episodeId: Int): EpisodeResult

    suspend fun getAllFavoriteCharacters(): Flow<List<CharactersDomain>>

    suspend fun insertMyFavoriteList(character: CharactersDomain)

    suspend fun deleteCharacterFromMyFavoriteList(characterId: Int)
}