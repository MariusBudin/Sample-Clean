package com.mariusbudin.sampleclean.data.characters.model.remote

data class CharacterLocationRemoteModel(
    val name: String,
    val url: String
) {
    companion object {
        val empty = CharacterLocationRemoteModel("", "")
    }
}