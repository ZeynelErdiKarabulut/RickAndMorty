package com.rickyandmorty.presentation.episode.state

import androidx.paging.PagingData
import com.rickyandmorty.domain.model.EpisodeListItem

data class EpisodeListState(
    val episodeList: PagingData<EpisodeListItem>? = PagingData.empty(),
    val isLoading: Boolean = false,
    val error: String? = null
)
