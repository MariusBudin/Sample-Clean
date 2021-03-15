package com.mariusbudin.sampleclean.features.characters.data.model.remote

import com.mariusbudin.sampleclean.features.characters.data.model.Character

data class CharactersListRemoteModel(
    val info: InfoRemoteModel,
    val results: List<CharacterRemoteModel>
){
    companion object{
        val empty = CharactersListRemoteModel(InfoRemoteModel.empty, emptyList())
    }
}