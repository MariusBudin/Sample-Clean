package com.mariusbudin.sampleclean.data.characters.model.remote

data class CharactersListRemoteModel(
    val info: InfoRemoteModel,
    val results: List<CharacterRemoteModel>
){
    companion object{
        val empty = CharactersListRemoteModel(InfoRemoteModel.empty, emptyList())
    }
}