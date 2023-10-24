package com.example.rickandmortycoursework.domain.model

sealed class EpisodeListItem() {
    data class EpisodeItem(val episode: EpisodeDomain) : EpisodeListItem()

    data class SeparatorItem(val season: String) : EpisodeListItem()
}

val EpisodeListItem.EpisodeItem.season: Int
    get() = this.episode.episode.substring(2, 3).toInt()
