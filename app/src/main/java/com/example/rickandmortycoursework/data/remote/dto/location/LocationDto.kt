package com.example.rickandmortycoursework.data.remote.dto.location

import com.example.rickandmortycoursework.data.remote.dto.Info
import com.example.rickandmortycoursework.domain.model.LocationDomain


data class LocationDto(
    val info: Info,
    val results: List<LocationResults>
)


fun LocationDto.toLocationDomain(): List<LocationDomain> {

    return results.map {
        LocationDomain(
            dimension = it.dimension,
            id = it.id,
            name = it.name,
            type = it.type
        )
    }
}

