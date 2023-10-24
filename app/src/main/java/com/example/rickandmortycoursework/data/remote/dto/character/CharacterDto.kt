package com.example.rickandmortycoursework.data.remote.dto.character

import com.example.rickandmortycoursework.domain.model.CharacterDomain


data class CharacterDto(
    val result: CharacterData
)

fun CharacterDto.toCharacter(): CharacterDomain {
    return CharacterDomain(
        result = result
    )
}