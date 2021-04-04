package com.mariusbudin.sampleclean.data.episodes.model.remote

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "episodes")
data class EpisodeRemoteModel(
    @PrimaryKey val id: Int,
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