package com.example.rickandmortycoursework.domain.repository

import androidx.paging.PagingData
import com.example.rickandmortycoursework.data.remote.dto.character.CharacterData
import com.example.rickandmortycoursework.data.remote.dto.episode.EpisodeResult
import com.example.rickandmortycoursework.data.remote.dto.location.LocationResults
import com.example.rickandmortycoursework.domain.model.CharactersDomain
import com.example.rickandmortycoursework.domain.model.EpisodeDomain
import com.example.rickandmortycoursework.domain.model.LocationDomain
import com.example.rickandmortycoursework.util.GenderState
import com.example.rickandmortycoursework.util.StatusState
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Path

interface RickAndMortyRepository {

    fun getAllCharacters(
        status: StatusState = StatusState.NONE,
        gender: GenderState = GenderState.NONE,
        name: String = "",
    ): Flow<PagingData<CharacterData>>


    suspend fun getCharacterDetailById(characterId: Int): CharacterData

    fun getAllLocation(): Flow<PagingData<LocationDomain>>

    suspend fun getLocationDetailById(locationId: Int): LocationResults

    fun getAllEpisode(): Flow<PagingData<EpisodeDomain>>

    suspend fun getEpisodeById(@Path("id") episodeId: Int): EpisodeResult

    fun getAllFavoriteCharacters(): Flow<List<CharactersDomain>>

    suspend fun insertMyFavoriteList(character: CharactersDomain)

    suspend fun deleteCharacterFromMyFavoriteList(character: CharactersDomain)
}
