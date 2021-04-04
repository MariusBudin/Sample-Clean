package com.mariusbudin.sampleclean.data.episodes

import com.mariusbudin.sampleclean.data.episodes.model.remote.EpisodeListRemoteModel
import com.mariusbudin.sampleclean.data.episodes.model.remote.EpisodeRemoteModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

interface EpisodesApi {
    @GET("episode")
    fun getEpisodes(): Call<EpisodeListRemoteModel>

    @GET("episode/{id}")
    fun getEpisode(@Path("id") id: Int): Call<EpisodeRemoteModel>

    class Service @Inject constructor(retrofit: Retrofit) : EpisodesApi {
        private val api by lazy { retrofit.create(EpisodesApi::class.java) }

        override fun getEpisodes() = api.getEpisodes()
        override fun getEpisode(id: Int) = api.getEpisode(id)
    }
}