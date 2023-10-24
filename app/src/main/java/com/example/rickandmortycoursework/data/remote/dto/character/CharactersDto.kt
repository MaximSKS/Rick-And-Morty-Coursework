package com.example.rickandmortycoursework.data.remote.dto.character

import com.example.rickandmortycoursework.data.remote.dto.Info


data class CharactersDto(
    val info: Info,
    val results: List<CharacterData>
)

