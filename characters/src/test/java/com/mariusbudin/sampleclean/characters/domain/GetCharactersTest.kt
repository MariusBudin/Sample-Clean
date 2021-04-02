package com.mariusbudin.sampleclean.characters.domain

import com.mariusbudin.sampleclean.characters.UnitTest
import com.mariusbudin.sampleclean.characters.data.CharactersRepository
import com.mariusbudin.sampleclean.characters.data.model.Character
import com.mariusbudin.sampleclean.core.domain.UseCase
import com.mariusbudin.sampleclean.core.functional.Either
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCharactersTest : UnitTest() {

    private lateinit var getCharacters: GetCharacters

    @MockK
    private lateinit var repository: CharactersRepository

    @Before
    fun setUp() {
        getCharacters = GetCharacters(repository)
        every { repository.getCharacters() } returns Either.Right(listOf(Character.empty))
    }

    @Test
    fun `should get data from repository`() {
        runBlocking { getCharacters.run(UseCase.None()) }

        verify(exactly = 1) { repository.getCharacters() }
    }
}
