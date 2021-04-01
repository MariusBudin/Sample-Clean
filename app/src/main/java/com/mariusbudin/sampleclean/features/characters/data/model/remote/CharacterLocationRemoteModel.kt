package com.mariusbudin.sampleclean.features.characters.data.model.remote

data class CharacterLocationRemoteModel(
    val name: String,
    val url: String
) {
    companion object {
        val empty = CharacterLocationRemoteModel("", "")
    }
}