package com.rickyandmorty.presentation.character.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rickyandmorty.R
import com.rickyandmorty.data.remote.dto.character.toCharactersDomain
import com.rickyandmorty.domain.CharactersDomain
import com.rickyandmorty.domain.repository.RickAndMortyRepository
import com.rickyandmorty.presentation.character.viewmodel.states.CharacterActivityState
import com.rickyandmorty.presentation.character.viewmodel.states.ListType
import com.rickyandmorty.util.GenderState
import com.rickyandmorty.util.StatusState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: RickAndMortyRepository,
    private val app: Application
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterActivityState())
    val state: StateFlow<CharacterActivityState> get() = _state

    init {
        getAllFavoriteCharacters()


        viewModelScope.launch {

            getListData().collect { it ->
                _state.value = _state.value.copy(
                    characters = it
                )
            }
        }

        _state.value.favoriteCharacter.forEach {
            Timber.d(it.name)
        }


    }

    suspend fun getListData(): Flow<PagingData<CharactersDomain>> {

        var characterName = _state.value.queryCharacterName.value

        if (characterName == null) {
            characterName = ""
        }

        val list = _state.value.favoriteCharacter


        return repository.getAllCharacters(
            status = _state.value.statusState,
            gender = _state.value.genderState,
            characterName
        ).toCharactersDomain(list)
    }

    fun setStatusState(status: StatusState) {
        _state.value = _state.value.copy(
            statusState = status
        )
    }

    fun setGenderState(genderState: GenderState) {
        _state.value = _state.value.copy(
            genderState = genderState
        )
    }

    fun checkIfTheFilterHasBeenApplied(): Boolean {

        val statusValue = _state.value.statusState
        val genderValue = _state.value.genderState
        val characterName = _state.value.queryCharacterName

        if (statusValue == StatusState.NONE && genderValue == GenderState.NONE && characterName.value == "") {
            _state.value = _state.value.copy(
                isFilter = false
            )
        } else {
            _state.value = _state.value.copy(
                isFilter = true
            )
        }

        return _state.value.isFilter
    }


    fun insertMyFavoriteList(character: CharactersDomain) {
        viewModelScope.launch {
            try {
                repository.insertMyFavoriteList(character)
                updateToastMessage(app.getString(R.string.toast_message_success))
            } catch (e: Exception) {
                updateToastMessage(app.getString(R.string.toast_message_error))
            }
        }
        updateToastState()
    }

    fun getAllFavoriteCharacters() {
        viewModelScope.launch {
            repository.getAllFavoriteCharacters().collect {
                _state.value = _state.value.copy(
                    favoriteCharacter = it
                )
            }
        }


    }

    fun deleteCharacterFromMyFavoriteList(characterId: Int) {
        viewModelScope.launch {
            try {
                repository.deleteCharacterFromMyFavoriteList(characterId)
                updateToastMessage(app.getString(R.string.toast_message_success))
            } catch (e: Exception) {
                updateToastMessage(app.getString(R.string.toast_message_error))
            }
        }
        updateToastState()
    }

    private fun updateToastMessage(message: String) {
        _state.value = _state.value.copy(
            toastMessage = message
        )
    }

    private fun updateToastState() {
        _state.value = _state.value.copy(
            isShowToastMessage = true
        )
    }

    fun showedToastMessage() {

        _state.value = _state.value.copy(
            isShowToastMessage = false,
            toastMessage = ""
        )
    }


    private fun setListLayoutManager( newType: ListType) {
        _state.value = _state.value.copy(
            listType = newType
        )
    }

    fun getListType(): ListType {
        return _state.value.listType
    }

    fun getIsShowToastMessage(): Boolean {
        return _state.value.isShowToastMessage
    }

    fun getToastMessage(): String {
        return _state.value.toastMessage
    }

    fun setLayoutManager() {
        when (this.getListType()) {
            ListType.GridLayout -> this.setListLayoutManager(ListType.LinearLayout)
            else -> this.setListLayoutManager(ListType.GridLayout)
        }
    }

}