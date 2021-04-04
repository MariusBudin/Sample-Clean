package com.mariusbudin.domain.episodes

import com.mariusbudin.domain.UnitTest
import com.mariusbudin.sampleclean.data.episodes.EpisodesRepository
import com.mariusbudin.sampleclean.data.episodes.model.remote.EpisodeRemoteModel
import com.mariusbudin.sampleclean.core.domain.UseCase
import com.mariusbudin.sampleclean.core.functional.Either
import com.mariusbudin.sampleclean.domain.episodes.GetEpisodes
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetEpisodesTest : UnitTest() {

    private lateinit var getEpisodes: GetEpisodes

    @MockK
    private lateinit var repository: EpisodesRepository

    @Before
    fun setUp() {
        getEpisodes = GetEpisodes(repository)
        every { repository.getEpisodes() } returns Either.Right(listOf(EpisodeRemoteModel.empty))
    }

    @Test
    fun `should get data from repository`() {
        runBlocking { getEpisodes.run(UseCase.None()) }

        verify(exactly = 1) { repository.getEpisodes() }
    }
}
