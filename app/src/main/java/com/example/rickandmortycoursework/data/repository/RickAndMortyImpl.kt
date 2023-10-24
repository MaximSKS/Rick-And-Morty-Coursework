package com.example.rickandmortycoursework.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmortycoursework.data.local.RickAndMortyDao
import com.example.rickandmortycoursework.data.remote.RickyAndMortyApi
import com.example.rickandmortycoursework.data.remote.dto.character.CharacterData
import com.example.rickandmortycoursework.data.remote.dto.episode.EpisodeResult
import com.example.rickandmortycoursework.data.remote.dto.location.LocationResults
import com.example.rickandmortycoursework.domain.model.CharactersDomain
import com.example.rickandmortycoursework.domain.model.EpisodeDomain
import com.example.rickandmortycoursework.domain.model.LocationDomain
import com.example.rickandmortycoursework.domain.repository.RickAndMortyRepository
import com.example.rickandmortycoursework.paging.CharactersPagingDataSource
import com.example.rickandmortycoursework.paging.EpisodePagingDataSource
import com.example.rickandmortycoursework.paging.LocationPagingDataSource
import com.example.rickandmortycoursework.util.GenderState
import com.example.rickandmortycoursework.util.StatusState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RickAndMortyImpl @Inject constructor(
    val api: RickyAndMortyApi,
    private val dao: RickAndMortyDao,
) : RickAndMortyRepository {

    override fun getAllCharacters(
        status: StatusState,
        gender: GenderState,
        name: String,
    ): Flow<PagingData<CharacterData>> {
        return Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = {
                CharactersPagingDataSource(
                    api,
                    statusState = status,
                    genderState = gender,
                    nameQuery = name
                )
            }
        ).flow
    }


    override suspend fun getCharacterDetailById(characterId: Int): CharacterData {

        return api.getCharacter(characterId)
    }

    override fun getAllLocation(): Flow<PagingData<LocationDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = 25
            ),
            pagingSourceFactory = {
                LocationPagingDataSource(api)
            }
        ).flow
    }

    override suspend fun getLocationDetailById(locationId: Int): LocationResults {
        return api.getLocation(locationId)
    }

    override fun getAllEpisode(): Flow<PagingData<EpisodeDomain>> {
        return Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = {
                EpisodePagingDataSource(api)
            }
        ).flow
    }

    override suspend fun getEpisodeById(episodeId: Int): EpisodeResult {
        return api.getEpisodeById(episodeId)
    }

    override fun getAllFavoriteCharacters(): Flow<List<CharactersDomain>> {
        return dao.getAllFavoriteCharacters()
    }

    override suspend fun insertMyFavoriteList(character: CharactersDomain) {
        dao.insertFavoriteCharacter(character = character)
    }

    override suspend fun deleteCharacterFromMyFavoriteList(character: CharactersDomain) {
        dao.deleteFavoriteCharacter(character)
    }
}