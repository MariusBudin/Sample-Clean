package com.mariusbudin.sampleclean.data.characters

import com.mariusbudin.sampleclean.core.exception.Failure
import com.mariusbudin.sampleclean.core.functional.Either
import com.mariusbudin.sampleclean.data.UnitTest
import com.mariusbudin.sampleclean.data.characters.model.remote.*
import com.mariusbudin.sampleclean.data.common.platform.NetworkHandler
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

class CharactersRepositoryTest : UnitTest() {

    private lateinit var repository: CharactersRepository.Remote

    @MockK
    private lateinit var networkHandler: NetworkHandler

    @MockK
    private lateinit var api: CharactersApi

    @MockK
    private lateinit var charactersCall: Call<CharactersListRemoteModel>

    @MockK
    private lateinit var charactersResponse: Response<CharactersListRemoteModel>

    @Before
    fun setUp() {
        repository = CharactersRepository.Remote(networkHandler, api)
    }

    @Test
    fun `should return empty list by default`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { charactersResponse.body() } returns null
        every { charactersResponse.isSuccessful } returns true
        every { charactersCall.execute() } returns charactersResponse
        every { api.getCharacters() } returns charactersCall

        val characters = repository.getCharacters()

        characters shouldEqual Either.Right(emptyList<CharacterRemoteModel>())
        verify(exactly = 1) { api.getCharacters() }
    }

    @Test
    fun `should return character list from api`() {
        val character = CharacterRemoteModel(
            id = 1,
            name = "Rick",
            status = Status.ALIVE,
            species = "human",
            CharacterLocationRemoteModel.empty,
            image = "fake.url"
        )

        every { networkHandler.isNetworkAvailable() } returns true
        every { charactersResponse.body() } returns CharactersListRemoteModel(
            InfoRemoteModel.empty,
            listOf(character)
        )
        every { charactersResponse.isSuccessful } returns true
        every { charactersCall.execute() } returns charactersResponse
        every { api.getCharacters() } returns charactersCall

        val characters = repository.getCharacters()

        characters shouldEqual Either.Right(listOf(character))
        verify(exactly = 1) { api.getCharacters() }
    }

    @Test
    fun `api should return network failure when no connection`() {
        every { networkHandler.isNetworkAvailable() } returns false

        val characters = repository.getCharacters()

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
        every { charactersResponse.isSuccessful } returns false
        every { charactersCall.execute() } returns charactersResponse
        every { api.getCharacters() } returns charactersCall

        val characters = repository.getCharacters()

        characters shouldBeInstanceOf Either::class.java
        characters.isLeft shouldEqual true
        characters.fold(
            { failure -> failure shouldBeInstanceOf Failure.ServerError::class.java },
            {})
    }

    @Test
    fun `request should catch exceptions`() {
        every { networkHandler.isNetworkAvailable() } returns true
        every { charactersCall.execute() } returns charactersResponse
        every { api.getCharacters() } returns charactersCall

        val characters = repository.getCharacters()

        characters shouldBeInstanceOf Either::class.java
        characters.isLeft shouldEqual true
        characters.fold(
            { failure -> failure shouldBeInstanceOf Failure.ServerError::class.java },
            {})
    }
}