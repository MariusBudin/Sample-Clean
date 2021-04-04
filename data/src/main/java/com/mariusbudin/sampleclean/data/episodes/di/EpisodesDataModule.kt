package com.mariusbudin.sampleclean.data.episodes.di

import com.mariusbudin.sampleclean.data.common.AppDatabase
import com.mariusbudin.sampleclean.data.common.platform.NetworkHandler
import com.mariusbudin.sampleclean.data.episodes.EpisodesApi
import com.mariusbudin.sampleclean.data.episodes.EpisodesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EpisodesDataModule {
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
    fun provideEpisodesLocalRepository(db: AppDatabase) =
        EpisodesRepository.Local(db.episodesDao())
}