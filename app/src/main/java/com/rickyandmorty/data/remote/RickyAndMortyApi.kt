package com.rickyandmorty.data.remote

import com.rickyandmorty.data.remote.dto.character.CharacterData
import com.rickyandmorty.data.remote.dto.character.CharactersDto
import com.rickyandmorty.data.remote.dto.episode.EpisodeDto
import com.rickyandmorty.data.remote.dto.episode.EpisodeResult
import com.rickyandmorty.data.remote.dto.location.LocationDto
import com.rickyandmorty.data.remote.dto.location.LocationResults
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickyAndMortyApi {

    @GET("character")
    suspend fun getAllCharacters(
        @Query("status") status: String="",
        @Query("gender") gender: String="",
        @Query("name") name: String="",
        @Query("page") page: Int? = null
    ): CharactersDto


    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): CharacterData

    @GET("location")
    suspend fun getAllLocation(@Query("page") page: Int? = null): LocationDto

    @GET("location/{id}")
    suspend fun getLocation(@Path("id") locationId: Int): LocationResults

    @GET("episode")
    suspend fun getAllEpisode(@Query("page") page: Int? = null): EpisodeDto

    @GET("episode/{id}")
    suspend fun getEpisodeById(@Path("id") episodeId: Int): EpisodeResult


}