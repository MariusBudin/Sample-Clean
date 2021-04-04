package com.mariusbudin.sampleclean.data.characters.di

import com.mariusbudin.sampleclean.data.characters.CharactersApi
import com.mariusbudin.sampleclean.data.characters.CharactersRepository
import com.mariusbudin.sampleclean.data.common.AppDatabase
import com.mariusbudin.sampleclean.data.common.platform.NetworkHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharactersDataModule {
    @Provides
    fun provideCharactersApi(retrofit: Retrofit): CharactersApi =
        retrofit.create(CharactersApi::class.java)

    @Singleton
    @Provides
    fun provideCharactersRepository(
        remote: CharactersRepository.Remote,
        local: CharactersRepository.Local
    ) = CharactersRepository(remote, local)

    @Singleton
    @Provides
    fun provideCharactersRemoteRepository(
        networkHandler: NetworkHandler,
        api: CharactersApi
    ) = CharactersRepository.Remote(networkHandler, api)

    @Singleton
    @Provides
    fun provideCharactersLocalRepository(db: AppDatabase) =
        CharactersRepository.Local(db.characterDao())
}