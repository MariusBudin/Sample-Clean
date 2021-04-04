package com.mariusbudin.sampleclean.data.episodes.model.remote

import com.mariusbudin.sampleclean.data.characters.model.remote.InfoRemoteModel

data class EpisodeListRemoteModel(
    val info: InfoRemoteModel,
    val results: List<EpisodeRemoteModel>
) {
    companion object {
        val empty = EpisodeListRemoteModel(InfoRemoteModel.empty, emptyList())
    }
}