package com.mariusbudin.sampleclean.features.characters.data.model.remote

data class LocationRemoteModel(
    val name: String,
    val url: String
) {
    companion object {
        val empty = LocationRemoteModel("", "")
    }
}