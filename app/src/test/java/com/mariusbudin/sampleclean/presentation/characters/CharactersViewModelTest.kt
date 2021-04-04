package com.mariusbudin.sampleclean.presentation.characters

import com.mariusbudin.sampleclean.domain.characters.GetCharacters
import com.mariusbudin.sampleclean.AndroidTest
import com.mariusbudin.sampleclean.domain.characters.model.CharacterModel
import com.mariusbudin.sampleclean.core.functional.Either
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
        val rick = CharacterModel(
            id = 1,
            name = "Rick",
            status = "Alive",
            location = "Earth",
            species = "human",
            image = "fake.url"
        )
        val morty = CharacterModel(
            id = 1,
            name = "Morty",
            status = "Alive",
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