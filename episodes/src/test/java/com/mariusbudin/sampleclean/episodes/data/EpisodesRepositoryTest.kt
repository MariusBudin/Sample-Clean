package com.mariusbudin.sampleclean.episodes.data

import com.mariusbudin.sampleclean.core.data.model.remote.InfoRemoteModel
import com.mariusbudin.sampleclean.core.exception.Failure
import com.mariusbudin.sampleclean.core.functional.Either
import com.mariusbudin.sampleclean.core.platform.NetworkHandler
import com.mariusbudin.sampleclean.episodes.UnitTest
import com.mariusbudin.sampleclean.episodes.data.model.Episode
import com.mariusbudin.sampleclean.episodes.data.model.remote.EpisodeListRemoteModel
import com.mariusbudin.sampleclean.episodes.data.model.remote.EpisodeRemoteModel
import io.mockk.Called
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class EpisodesRepositoryTest : UnitTest() {

    private lateinit var repository: EpisodesRepository.Remote

    @MockK
    private lateinit var networkHandler: NetworkHandler

    @MockK
    private lateinit var api: EpisodesApi

    @MockK
    private lateinit var episodesCall: Call<EpisodeListRemoteModel>

    @MockK
    private lateinit var episodesResponse: Response<EpisodeListRemoteModel>

    @Before
    fun setUp() {
        repository = EpisodesRepository.Remote(networkHandler, api)
    }

    @Test
    fun `should return empty list by default`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { episodesResponse.body() } returns null
        every { episodesResponse.isSuccessful } returns true
        every { episodesCall.execute() } returns episodesResponse
        every { api.getEpisodes() } returns episodesCall

        val characters = repository.getEpisodes()

        characters shouldEqual Either.Right(emptyList<Character>())
        verify(exactly = 1) { api.getEpisodes() }
    }

    @Test
    fun `should return character list from api`() {
        val episode = EpisodeRemoteModel(
            id = 1,
            name = "Pilot",
            airDate = "",
            episode = "",
            characters = emptyList(),
            url = "",
            created = ""
        )

        every { networkHandler.isNetworkAvailable() } returns true
        every { episodesResponse.body() } returns EpisodeListRemoteModel(
            InfoRemoteModel.empty,
            listOf(episode)
        )
        every { episodesResponse.isSuccessful } returns true
        every { episodesCall.execute() } returns episodesResponse
        every { api.getEpisodes() } returns episodesCall

        val characters = repository.getEpisodes()

        characters shouldEqual Either.Right(listOf(Episode.mapFromRemoteModel(episode)))
        verify(exactly = 1) { api.getEpisodes() }
    }

    @Test
    fun `api should return network failure when no connection`() {
        every { networkHandler.isNetworkAvailable() } returns false

        val characters = repository.getEpisodes()

        characters shouldBeInstanceOf Either::class.java
        characters.isLeft shouldEqual true
        characters.fold(
            { failure -> failure shouldBeInstanceOf Failure.NetworkConnection::class.java },
            {})
        verify { api wasNot Called }
    }

    @Test
    fun `api should return server error if no successful response`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { episodesResponse.isSuccessful } returns false
        every { episodesCall.execute() } returns episodesResponse
        every { api.getEpisodes() } returns episodesCall

        val characters = repository.getEpisodes()

        characters shouldBeInstanceOf Either::class.java
        characters.isLeft shouldEqual true
        characters.fold(
            { failure -> failure shouldBeInstanceOf Failure.ServerError::class.java },
            {})
    }

    @Test
    fun `request should catch exceptions`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { episodesCall.execute() } returns episodesResponse
        every { api.getEpisodes() } returns episodesCall

        val characters = repository.getEpisodes()

        characters shouldBeInstanceOf Either::class.java
        characters.isLeft shouldEqual true
        characters.fold(
            { failure -> failure shouldBeInstanceOf Failure.ServerError::class.java },
            {})
    }
}