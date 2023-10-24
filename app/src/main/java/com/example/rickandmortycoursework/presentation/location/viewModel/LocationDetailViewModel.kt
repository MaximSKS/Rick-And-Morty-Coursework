package com.example.rickandmortycoursework.presentation.location.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortycoursework.data.remote.dto.character.toCharactersDomain
import com.example.rickandmortycoursework.data.remote.dto.location.toLocationByIdDomain
import com.example.rickandmortycoursework.domain.model.CharactersDomain
import com.example.rickandmortycoursework.domain.repository.RickAndMortyRepository
import com.example.rickandmortycoursework.presentation.location.state.LocationDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    val repository: RickAndMortyRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _state = MutableStateFlow(LocationDetailState())
    val state: StateFlow<LocationDetailState> get() = _state

    init {
        savedStateHandle.get<Int>("locationId")?.let {
            getLocationInfo(locationId = it)
        }
    }

    fun getLocationInfo(locationId: Int) {
        if (_state.value.characterList == null) {
            try {
                _state.value = _state.value.copy(
                    isLoading = true
                )

                viewModelScope.launch {
                    val data =
                        repository.getLocationDetailById(locationId).toLocationByIdDomain()

                    _state.value = _state.value.copy(
                        locationInfo = data
                    )

                    val characterList = mutableListOf<CharactersDomain>()
                    data.residents.forEach {
                        val characterDeferred = async {
                            val characterID = (it.split("/"))[5].toInt()
                            val characters = repository.getCharacterDetailById(characterID)
                            characters
                        }
                        characterList.add(characterDeferred.await().toCharactersDomain())
                    }
                    _state.value = _state.value.copy(
                        isLoading = false
                    )

                    _state.value = _state.value.copy(
                        characterList = characterList
                    )
                }

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "An unexpected error occurred!"
                )
            } catch (e: HttpException) {
                _state.value = _state.value.copy(
                    error = "Please check your internet connection!"
                )
            }
        }
    }
}