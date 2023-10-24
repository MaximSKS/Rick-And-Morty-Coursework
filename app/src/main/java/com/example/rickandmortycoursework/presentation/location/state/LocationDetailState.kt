package com.example.rickandmortycoursework.presentation.location.state

import com.example.rickandmortycoursework.domain.model.CharactersDomain
import com.example.rickandmortycoursework.domain.model.LocationByIdDomain

data class LocationDetailState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val locationInfo: LocationByIdDomain? = null,
    val locationID: Int = 0,
    val characterList: List<CharactersDomain>? = null
)