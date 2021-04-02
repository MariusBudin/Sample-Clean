package com.mariusbudin.sampleclean.core.data.model.remote

data class InfoRemoteModel(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?,
) {
    companion object {
        val empty = InfoRemoteModel(0, 0, "", "")
    }
}