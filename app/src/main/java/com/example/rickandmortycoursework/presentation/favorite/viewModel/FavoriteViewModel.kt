package com.example.rickandmortycoursework.presentation.favorite.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortycoursework.domain.repository.RickAndMortyRepository
import com.example.rickandmortycoursework.domain.model.CharactersDomain
import com.example.rickandmortycoursework.presentation.favorite.FavoriteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    val repository: RickAndMortyRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FavoriteState())
    val state: StateFlow<FavoriteState> get() = _state


    init {
        viewModelScope.launch {
            try {
                getFavoriteCharacters().collect {
                    _state.value = _state.value.copy(
                        characterList = it,
                        isError = true
                    )
                }

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    characterList = emptyList(),
                    isError = true
                )
            }

        }

    }

     fun getFavoriteCharacters(): Flow<List<CharactersDomain>> {
        return repository.getAllFavoriteCharacters()
    }

    fun deleteCharacter(charactersDomain: CharactersDomain) {
        viewModelScope.launch {
            repository.deleteCharacterFromMyFavoriteList(charactersDomain)
        }
    }

}