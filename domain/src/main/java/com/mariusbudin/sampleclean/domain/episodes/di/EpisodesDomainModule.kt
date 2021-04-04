package com.mariusbudin.sampleclean.domain.episodes.di

import com.mariusbudin.sampleclean.data.episodes.EpisodesRepository
import com.mariusbudin.sampleclean.domain.episodes.GetEpisodes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EpisodesDomainModule {

    @Singleton
    @Provides
    fun provideGetEpisodes(repository: EpisodesRepository) =
        GetEpisodes(repository)
}