package com.example.rickandmortycoursework.presentation.episode.state

import androidx.paging.PagingData
import com.example.rickandmortycoursework.domain.model.EpisodeListItem

data class EpisodeListState(
    val episodeList: PagingData<EpisodeListItem>? = PagingData.empty(),

    )
