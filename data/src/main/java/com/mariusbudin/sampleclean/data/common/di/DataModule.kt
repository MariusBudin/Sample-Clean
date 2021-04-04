package com.mariusbudin.sampleclean.data.common.di

import android.content.Context
import com.mariusbudin.sampleclean.data.BuildConfig
import com.mariusbudin.sampleclean.data.common.AppDatabase
import com.mariusbudin.sampleclean.data.common.platform.NetworkHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    private const val API_BASE_URL = "https://rickandmortyapi.com/api/"

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .client(createClient())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun provideNetworkHandler(@ApplicationContext appContext: Context) =
        NetworkHandler(appContext)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)
}