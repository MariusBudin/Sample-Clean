package com.mariusbudin.sampleclean.features.episodes.data.model.remote

import com.squareup.moshi.Json

data class EpisodeRemoteModel(
    val id: Int,
    val name: String,
    @field:Json(name = "air_date") val airDate: String?,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String
) {
    companion object {
        val empty = EpisodeRemoteModel(0, "", "", "", emptyList(), "", "")
    }
}