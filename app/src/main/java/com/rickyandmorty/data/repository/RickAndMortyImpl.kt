package com.rickyandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rickyandmorty.data.local.RickAndMortyDao
import com.rickyandmorty.data.remote.RickyAndMortyApi
import com.rickyandmorty.data.remote.dto.character.CharacterData
import com.rickyandmorty.data.remote.dto.episode.EpisodeResult
import com.rickyandmorty.data.remote.dto.location.LocationResults
import com.rickyandmorty.domain.CharactersDomain
import com.rickyandmorty.domain.model.EpisodeDomain
import com.rickyandmorty.domain.model.LocationDomain
import com.rickyandmorty.domain.repository.RickAndMortyRepository
import com.rickyandmorty.paging.CharactersPagingDataSource
import com.rickyandmorty.paging.EpisodePagingDataSource
import com.rickyandmorty.paging.LocationPagingDataSource
import com.rickyandmorty.util.GenderState
import com.rickyandmorty.util.StatusState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RickAndMortyImpl @Inject constructor(
    val api: RickyAndMortyApi,
    private val dao: RickAndMortyDao
) : RickAndMortyRepository {


    override suspend fun getAllCharacters(
        status: StatusState,
        gender: GenderState,
        name: String
    ): Flow<PagingData<CharacterData>> {
        return Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = {
                CharactersPagingDataSource(
                    api,
                    statusState = status,
                    genderState = gender,
                    nameQuery = name
                )
            }
        ).flow
    }


    override suspend fun getCharacterDetailById(characterId: Int): CharacterData {

        return api.getCharacter(characterId)
    }

    override suspend fun getAllLocation(): Flow<PagingData<LocationDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25
            ),
            pagingSourceFactory = {
                LocationPagingDataSource(api)
            }
        ).flow
    }

    override suspend fun getLocationDetailById(locationId: Int): LocationResults {
        return api.getLocation(locationId)
    }

    override suspend fun getAllEpisode(): Flow<PagingData<EpisodeDomain>> {
        return Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = {
                EpisodePagingDataSource(api)
            }
        ).flow
    }

    override suspend fun getEpisodeById(episodeId: Int): EpisodeResult {
        return api.getEpisodeById(episodeId)
    }

    override suspend fun getAllFavoriteCharacters(): Flow<List<CharactersDomain>> {
        return dao.getAllFavoriteCharacters()
    }

    override suspend fun insertMyFavoriteList(character: CharactersDomain) {
        dao.insertFavoriteCharacter(character = character)
    }

    override suspend fun deleteCharacterFromMyFavoriteList(characterId: Int) {
        dao.deleteFavoriteCharacter(characterId)
    }


}