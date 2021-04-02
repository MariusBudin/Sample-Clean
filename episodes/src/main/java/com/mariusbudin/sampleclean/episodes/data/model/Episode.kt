package com.mariusbudin.sampleclean.episodes.data.model

import androidx.recyclerview.widget.DiffUtil
import com.mariusbudin.sampleclean.episodes.data.model.remote.EpisodeRemoteModel

data class Episode(
    val id: Int,
    val name: String,
    val airDate: String?,
    val episode: String
) {
    companion object {

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