package com.example.rickandmortycoursework.paging

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortycoursework.data.remote.RickyAndMortyApi
import com.example.rickandmortycoursework.domain.model.LocationDomain
import com.example.rickandmortycoursework.data.remote.dto.location.toLocationDomain
import javax.inject.Inject


class LocationPagingDataSource @Inject constructor(
    val api: RickyAndMortyApi
) : PagingSource<Int, LocationDomain>() {

    override fun getRefreshKey(state: PagingState<Int, LocationDomain>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationDomain> {

        val pageNumber = params.key ?: 1

        return try {
            val locationDto = api.getAllLocation(pageNumber)
            val data = locationDto.toLocationDomain()


            var nextPageNumber: Int? = null

            if (locationDto.info.next != null) {
                val uri = Uri.parse(locationDto.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = data,
                prevKey = if (pageNumber == 1) null else pageNumber - 1,
                nextKey = nextPageNumber
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }


    }
}
