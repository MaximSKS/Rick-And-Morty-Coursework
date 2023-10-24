package com.example.rickandmortycoursework.presentation.character.viewmodel.states

import com.example.rickandmortycoursework.data.remote.dto.character.CharacterData
import com.example.rickandmortycoursework.domain.model.EpisodeDomain

data class CharacterDetailState(
    val character: CharacterData? = null,
    val navigateArgLocationId: Int? = null,
    val episodeList: List<EpisodeDomain>? = null,
    val isLoadingEpisodeInfo: Boolean = false
)