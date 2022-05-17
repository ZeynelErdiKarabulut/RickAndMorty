package com.rickyandmorty.presentation.location.state

import androidx.paging.PagingData
import com.rickyandmorty.domain.model.LocationDomain

data class LocationListState(
    val locationData: PagingData<LocationDomain>? = PagingData.empty()
)