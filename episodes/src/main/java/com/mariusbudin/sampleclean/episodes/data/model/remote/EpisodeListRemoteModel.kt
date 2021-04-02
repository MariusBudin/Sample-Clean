package com.mariusbudin.sampleclean.episodes.data.model.remote

import com.mariusbudin.sampleclean.core.data.model.remote.InfoRemoteModel

data class EpisodeListRemoteModel(
    val info: InfoRemoteModel,
    val results: List<EpisodeRemoteModel>
) {
    companion object {
        val empty = EpisodeListRemoteModel(InfoRemoteModel.empty, emptyList())
    }
}