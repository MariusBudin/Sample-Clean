package com.mariusbudin.sampleclean.presentation.episodes.model

import androidx.recyclerview.widget.DiffUtil
import com.mariusbudin.sampleclean.domain.episodes.model.EpisodeModel

data class Episode(
    val id: Int,
    val name: String,
    val airDate: String?,
    val episode: String
) {
    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<Episode> =
            object : DiffUtil.ItemCallback<Episode>() {
                override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                    return oldItem == newItem
                }
            }

        fun EpisodeModel.mapToPresentationModel() =
            Episode(
                id = id,
                name = name,
                airDate = airDate,
                episode = episode
            )
    }
}