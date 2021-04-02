package com.mariusbudin.sampleclean.episodes.domain

import com.mariusbudin.sampleclean.UnitTest
import com.mariusbudin.sampleclean.core.domain.UseCase
import com.mariusbudin.sampleclean.core.functional.Either
import com.mariusbudin.sampleclean.episodes.data.EpisodesRepository
import com.mariusbudin.sampleclean.episodes.data.model.Episode
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
        every { repository.getEpisodes() } returns Either.Right(listOf(Episode.empty))
    }

    @Test
    fun `should get data from repository`() {
        runBlocking { getEpisodes.run(UseCase.None()) }

        verify(exactly = 1) { repository.getEpisodes() }
    }
}
