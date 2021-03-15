package com.mariusbudin.sampleclean.features.characters.di

import com.mariusbudin.sampleclean.core.data.AppDatabase
import com.mariusbudin.sampleclean.core.platform.NetworkHandler
import com.mariusbudin.sampleclean.features.characters.data.CharactersApi
import com.mariusbudin.sampleclean.features.characters.data.CharactersRepository
import com.mariusbudin.sampleclean.features.characters.domain.GetCharacterDetails
import com.mariusbudin.sampleclean.features.characters.domain.GetCharacters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharactersModule {
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

    @Singleton
    @Provides
    fun provideGetCharacters(repository: CharactersRepository) =
        GetCharacters(repository)

    @Singleton
    @Provides
    fun provideGetCharacterDetails(repository: CharactersRepository) =
        GetCharacterDetails(repository)
}