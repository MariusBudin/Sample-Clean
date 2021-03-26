package com.mariusbudin.sampleclean.features.characters.presentation

import com.mariusbudin.sampleclean.AndroidTest
import com.mariusbudin.sampleclean.core.functional.Either
import com.mariusbudin.sampleclean.features.characters.data.model.Character
import com.mariusbudin.sampleclean.features.characters.data.model.remote.Status
import com.mariusbudin.sampleclean.features.characters.domain.GetCharacters
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.shouldEqualTo
import org.junit.Before
import org.junit.Test

class CharactersViewModelTest : AndroidTest() {

    private lateinit var viewModel: CharactersViewModel

    @MockK
    private lateinit var getCharacters: GetCharacters

    @Before
    fun setUp() {
        viewModel = CharactersViewModel(getCharacters)
    }

    @Test
    fun `loading characters should update live data`() {
        val rick = Character(
            id = 1,
            name = "Rick",
            status = Status.Alive.name,
            location = "Earth",
            species = "human",
            image = "fake.url"
        )
        val morty = Character(
            id = 1,
            name = "Morty",
            status = Status.Alive.name,
            location = "Earth",
            species = "human",
            image = "another.fake.url"
        )
        val characters = listOf(rick, morty)
        coEvery { getCharacters.run(any()) } returns Either.Right(characters)

        viewModel.characters.observeForever {
            it!!.size shouldEqualTo 2
            it[0].id shouldEqualTo 0
            it[0].name shouldEqualTo "Rick"
            it[1].id shouldEqualTo 1
            it[1].species shouldEqualTo "human"
        }

        runBlocking { viewModel.getCharacters() }
    }
}