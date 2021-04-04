package com.mariusbudin.sampleclean.domain.characters.di

import com.mariusbudin.sampleclean.data.characters.CharactersRepository
import com.mariusbudin.sampleclean.domain.characters.GetCharacterDetails
import com.mariusbudin.sampleclean.domain.characters.GetCharacters
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CharactersDomainModule {

    @Singleton
    @Provides
    fun provideGetCharacters(repository: CharactersRepository) =
        GetCharacters(repository)

    @Singleton
    @Provides
    fun provideGetCharacterDetails(repository: CharactersRepository) =
        GetCharacterDetails(repository)
}