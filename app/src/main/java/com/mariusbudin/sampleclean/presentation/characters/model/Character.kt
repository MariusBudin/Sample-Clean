package com.mariusbudin.sampleclean.presentation.characters.model

import androidx.recyclerview.widget.DiffUtil
import com.mariusbudin.sampleclean.domain.characters.model.CharacterModel

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val location: String,
    val image: String
) {
    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<Character> =
            object : DiffUtil.ItemCallback<Character>() {
                override fun areItemsTheSame(
                    oldItem: Character,
                    newItem: Character
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Character,
                    newItem: Character
                ): Boolean {
                    return oldItem == newItem
                }
            }

        fun CharacterModel.mapToPresentationModel() =
            Character(
                id = id,
                name = name,
                status = status,
                species = species,
                location = location,
                image = image
            )
    }
}