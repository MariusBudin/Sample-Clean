package com.mariusbudin.sampleclean.episodes.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mariusbudin.sampleclean.R
import com.mariusbudin.sampleclean.databinding.ItemEpisodeBinding
import com.mariusbudin.sampleclean.episodes.data.model.Episode

class EpisodesAdapter(
    private val onSelect: (id: Int) -> Unit
) : ListAdapter<Episode, EpisodesAdapter.EpisodeViewHolder>(Episode.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding: ItemEpisodeBinding =
            ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) =
        holder.bind(getItem(position), onSelect)

    class EpisodeViewHolder(private val binding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Episode,
            onSelect: (id: Int) -> Unit
        ) {
            with(item) {
                binding.name.text = name
                binding.episode.text = episode
//                binding.date.text = itemView.resources.getString(R.string.episodes_aired_on, airDate)
                itemView.setOnClickListener { onSelect(id) }
            }
        }
    }
}