package com.example.rickandmortycoursework.presentation.location.state

import androidx.paging.PagingData
import com.example.rickandmortycoursework.domain.model.LocationDomain


data class LocationListState(
    val locationData: PagingData<LocationDomain>? = PagingData.empty()
)