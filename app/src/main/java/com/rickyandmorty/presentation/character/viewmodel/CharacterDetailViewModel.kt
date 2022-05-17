package com.rickyandmorty.presentation.character.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickyandmorty.data.remote.dto.episode.toEpisodeDomain
import com.rickyandmorty.domain.model.EpisodeDomain
import com.rickyandmorty.domain.repository.RickAndMortyRepository
import com.rickyandmorty.presentation.character.viewmodel.states.CharacterDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val repository: RickAndMortyRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterDetailState())
    val state: StateFlow<CharacterDetailState> get() = _state


    private fun getCharacter(characterID: Int) {

        viewModelScope.launch {
            val data = repository.getCharacterDetailById(characterID)

            _state.value = _state.value.copy(
                character = data
            )

            val episodeList = _state.value.character!!.episode


            val list: MutableList<EpisodeDomain> = mutableListOf()



            episodeList.let {
                episodeList.forEachIndexed { index, episodeUrl ->

                    val episodeId = (episodeUrl.split("/"))[5]

                    val episode = repository.getEpisodeById(episodeId.toInt())

                    list.add(episode.toEpisodeDomain())


                }

                _state.value = _state.value.copy(
                    episodeList = list
                )
            }


        }

    }

    fun setCharacterId(id: Int) {
        _state.value = _state.value.copy(
            characterIdFromCharacterListFragment = id
        )
    }

    fun getCharacterInvoke() {
        getCharacter(getCharacterIDFromFragmentList())

    }

    private fun getCharacterIDFromFragmentList(): Int {
        return _state.value.characterIdFromCharacterListFragment
    }

    fun setNavigateLocationId(locationId: Int) {
        _state.value = _state.value.copy(
            navigateArgLocationId = locationId
        )
    }

    fun getNavigationLocationID(): Int? {
        return _state.value.navigateArgLocationId
    }

    fun displayDetailComplete() {
        _state.value = _state.value.copy(
            navigateArgLocationId = null
        )
    }

}

