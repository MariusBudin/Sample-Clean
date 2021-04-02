package com.mariusbudin.sampleclean.characters.data

import com.mariusbudin.sampleclean.characters.data.model.remote.CharacterRemoteModel
import com.mariusbudin.sampleclean.characters.data.model.remote.CharactersListRemoteModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

interface CharactersApi {
    @GET("character")
    fun getCharacters(): Call<CharactersListRemoteModel>

    @GET("character/{id}")
    fun getCharacter(@Path("id") id: Int): Call<CharacterRemoteModel>

    class Service @Inject constructor(retrofit: Retrofit) : CharactersApi {
        private val api by lazy { retrofit.create(CharactersApi::class.java) }

        override fun getCharacters() = api.getCharacters()
        override fun getCharacter(id: Int) = api.getCharacter(id)
    }
}