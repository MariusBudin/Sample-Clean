package com.mariusbudin.sampleclean.domain.episodes.model

import com.mariusbudin.sampleclean.data.episodes.model.remote.EpisodeRemoteModel

data class EpisodeModel(
    val id: Int,
    val name: String,
    val airDate: String?,
    val episode: String
) {
    companion object {
        val empty = EpisodeModel(0, "", "", "")

        fun EpisodeRemoteModel.mapToDomainModel() =
            EpisodeModel(
                id = id,
                name = name,
                airDate = airDate,
                episode = episode
            )
    }
}