package com.mariusbudin.sampleclean.characters.data.model.remote

data class CharacterRemoteModel(
    val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val location: CharacterLocationRemoteModel,
    val image: String
) {
    companion object {
        val empty = CharacterRemoteModel(0, "", Status.unknown, "", CharacterLocationRemoteModel.empty, "")
    }
}

enum class Status { Alive, Dead, unknown }