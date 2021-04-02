package com.mariusbudin.sampleclean.characters.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mariusbudin.sampleclean.characters.data.model.Character
import com.mariusbudin.sampleclean.core.extension.load
import com.mariusbudin.sampleclean.databinding.ItemCharacterBinding

class CharactersAdapter(
    private val onSelect: (id: Int) -> Unit
) : ListAdapter<Character, CharactersAdapter.CharacterViewHolder>(Character.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding: ItemCharacterBinding =
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) =
        holder.bind(getItem(position), onSelect)

    class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: Character,
            onSelect: (id: Int) -> Unit
        ) {
            with(item) {
                binding.title.text = name
                binding.image.load(image)
                itemView.setOnClickListener { onSelect(id) }
            }
        }
    }
}