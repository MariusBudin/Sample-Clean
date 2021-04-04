package com.mariusbudin.sampleclean.presentation.episodes

import com.mariusbudin.sampleclean.domain.episodes.GetEpisodes
import com.mariusbudin.sampleclean.domain.episodes.model.EpisodeModel
import com.mariusbudin.sampleclean.AndroidTest
import com.mariusbudin.sampleclean.core.functional.Either
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test

class EpisodesViewModelTest : AndroidTest() {

    private lateinit var viewModel: EpisodesViewModel

    @MockK
    private lateinit var getEpisodes: GetEpisodes

    @Before
    fun setUp() {
        viewModel = EpisodesViewModel(getEpisodes)
    }

    @Test
    fun `loading episodes should update live data`() {
        val e1 = EpisodeModel(
            id = 1,
            name = "Pilot",
            airDate = "December 2, 2013",
            episode = "S01E01"

        )
        val e2 = EpisodeModel(
            id = 2,
            name = "Lawnmower Dog",
            airDate = "December 9, 2013",
            episode = "S01E02"
        )
        val episodes = listOf(e1, e2)
        coEvery { getEpisodes.run(any()) } returns Either.Right(episodes)

        viewModel.episodes.observeForever {
            it!!.size shouldEqualTo 2
            it[0].id shouldEqualTo 0
            it[0].name shouldEqualTo "Pilot"
            it[1].id shouldEqualTo 1
            it[1].episode shouldEqualTo "S01E02"
        }

        runBlocking { viewModel.getEpisodes() }
    }
}