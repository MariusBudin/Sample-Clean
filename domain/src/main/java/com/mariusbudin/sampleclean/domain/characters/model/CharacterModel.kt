package com.mariusbudin.sampleclean.domain.characters.model

import com.mariusbudin.sampleclean.data.characters.model.remote.CharacterRemoteModel

data class CharacterModel(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val location: String,
    val image: String
) {
    companion object {
        val empty = CharacterModel(0, "", "", "", "", "")

        fun CharacterRemoteModel.mapToDomainModel() =
            CharacterModel(
                id = id,
                name = name,
                status = status.name,
                species = species,
                location = location.name,
                image = image
            )
    }
}