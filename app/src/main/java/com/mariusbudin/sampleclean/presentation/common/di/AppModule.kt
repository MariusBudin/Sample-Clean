package com.mariusbudin.sampleclean.presentation.common.di

import com.mariusbudin.sampleclean.presentation.common.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideNavigator() = Navigator()
}