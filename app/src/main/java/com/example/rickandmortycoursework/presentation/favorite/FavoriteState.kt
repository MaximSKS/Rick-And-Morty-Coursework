package com.example.rickandmortycoursework.presentation.favorite

import com.example.rickandmortycoursework.domain.model.CharactersDomain

data class FavoriteState(
    val characterList: List<CharactersDomain> = emptyList(),
    val isError: Boolean = false
)