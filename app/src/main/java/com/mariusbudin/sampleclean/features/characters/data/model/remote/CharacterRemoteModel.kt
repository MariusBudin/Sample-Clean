package com.mariusbudin.sampleclean.features.characters.data.model.remote

data class CharacterRemoteModel(
    val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val location: LocationRemoteModel,
    val image: String
) {
    companion object {
        val empty = CharacterRemoteModel(0, "", Status.unknown, "", LocationRemoteModel.empty, "")
    }
}

enum class Status { Alive, Dead, unknown }