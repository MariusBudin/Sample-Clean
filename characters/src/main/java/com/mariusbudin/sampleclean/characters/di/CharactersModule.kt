package com.mariusbudin.sampleclean.characters.di

import com.mariusbudin.sampleclean.characters.data.CharactersApi
import com.mariusbudin.sampleclean.characters.data.CharactersRepository
import com.mariusbudin.sampleclean.characters.domain.GetCharacterDetails
import com.mariusbudin.sampleclean.characters.domain.GetCharacters
import com.mariusbudin.sampleclean.core.platform.NetworkHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharactersModule {

    @Singleton
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
    fun provideCharactersLocalRepository() = CharactersRepository.Local()

    @Singleton
    @Provides
    fun provideGetCharacters(repository: CharactersRepository) =
        GetCharacters(repository)

    @Singleton
    @Provides
    fun provideGetCharacterDetails(repository: CharactersRepository) =
        GetCharacterDetails(repository)
}