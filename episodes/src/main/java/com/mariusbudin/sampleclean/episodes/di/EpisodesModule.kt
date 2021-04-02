package com.mariusbudin.sampleclean.episodes.di

import com.mariusbudin.sampleclean.core.platform.NetworkHandler
import com.mariusbudin.sampleclean.episodes.data.EpisodesApi
import com.mariusbudin.sampleclean.episodes.data.EpisodesRepository
import com.mariusbudin.sampleclean.episodes.domain.GetEpisodes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EpisodesModule {

    @Singleton
    @Provides
    fun provideEpisodesApi(retrofit: Retrofit): EpisodesApi =
        retrofit.create(EpisodesApi::class.java)

    @Singleton
    @Provides
    fun provideEpisodesRepository(
        remote: EpisodesRepository.Remote,
        local: EpisodesRepository.Local
    ) = EpisodesRepository(remote, local)

    @Singleton
    @Provides
    fun provideEpisodesRemoteRepository(
        networkHandler: NetworkHandler,
        api: EpisodesApi
    ) = EpisodesRepository.Remote(networkHandler, api)

    @Singleton
    @Provides
    fun provideEpisodesLocalRepository() = EpisodesRepository.Local()

    @Singleton
    @Provides
    fun provideGetEpisodes(repository: EpisodesRepository) =
        GetEpisodes(repository)
}