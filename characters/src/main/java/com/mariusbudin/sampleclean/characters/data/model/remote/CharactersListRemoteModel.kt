package com.mariusbudin.sampleclean.characters.data.model.remote

import com.mariusbudin.sampleclean.core.data.model.remote.InfoRemoteModel

data class CharactersListRemoteModel(
    val info: InfoRemoteModel,
    val results: List<CharacterRemoteModel>
) {
    companion object {
        val empty = CharactersListRemoteModel(InfoRemoteModel.empty, emptyList())
    }
}