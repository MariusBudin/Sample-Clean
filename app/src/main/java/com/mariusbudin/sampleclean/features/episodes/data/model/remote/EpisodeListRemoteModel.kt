package com.mariusbudin.sampleclean.features.episodes.data.model.remote

import com.mariusbudin.sampleclean.features.characters.data.model.remote.InfoRemoteModel

data class EpisodeListRemoteModel(
    val info: InfoRemoteModel,
    val results: List<EpisodeRemoteModel>
) {
    companion object {
        val empty = EpisodeListRemoteModel(InfoRemoteModel.empty, emptyList())
    }
}