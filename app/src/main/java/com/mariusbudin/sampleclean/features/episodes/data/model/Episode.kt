package com.mariusbudin.sampleclean.features.episodes.data.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mariusbudin.sampleclean.features.episodes.data.model.Episode.Companion.TABLE_NAME
import com.mariusbudin.sampleclean.features.episodes.data.model.remote.EpisodeRemoteModel

@Entity(tableName = TABLE_NAME)
data class Episode(
    @PrimaryKey val id: Int,
    val name: String,
    val airDate: String?,
    val episode: String
) {
    companion object {
        const val TABLE_NAME = "episodes"

        val empty = Episode(0, "", "", "")

        var DIFF_CALLBACK: DiffUtil.ItemCallback<Episode> =
            object : DiffUtil.ItemCallback<Episode>() {
                override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                    return oldItem == newItem
                }
            }

        fun mapFromRemoteModel(remoteModel: EpisodeRemoteModel) =
            Episode(
                id = remoteModel.id,
                name = remoteModel.name,
                airDate = remoteModel.airDate,
                episode = remoteModel.episode
            )
    }
}