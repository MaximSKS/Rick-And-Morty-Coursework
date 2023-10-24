package com.example.rickandmortycoursework.data.remote.dto.episode

import com.example.rickandmortycoursework.data.remote.dto.Info
import com.example.rickandmortycoursework.domain.model.EpisodeDomain

data class EpisodeDto(
    val info: Info,
    val results: List<EpisodeResult>
)

fun EpisodeDto.toEpisodeDomain(): List<EpisodeDomain> {

    return results.map {
        EpisodeDomain(
            id = it.id,
            name = it.name,
            air_date = it.air_date,
            episode = it.episode,
            characters = it.characters
        )
    }
}