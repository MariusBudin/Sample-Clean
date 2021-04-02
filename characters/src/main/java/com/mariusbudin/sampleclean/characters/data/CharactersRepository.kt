package com.mariusbudin.sampleclean.characters.data

import com.mariusbudin.sampleclean.characters.data.model.Character
import com.mariusbudin.sampleclean.core.data.BaseRepository
import com.mariusbudin.sampleclean.core.exception.Failure
import com.mariusbudin.sampleclean.core.exception.Failure.NetworkConnection
import com.mariusbudin.sampleclean.core.functional.Either
import com.mariusbudin.sampleclean.core.functional.Either.Left
import com.mariusbudin.sampleclean.core.platform.NetworkHandler
import javax.inject.Inject

class CharactersRepository @Inject constructor(
    private val remote: Remote,
    private val local: Local
) {
    fun getCharacters() = remote.getCharacters()
    fun getCharacterDetails(id: Int) = remote.getCharacterDetails(id)

    class Remote @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val api: CharactersApi
    ) : BaseRepository.Remote(networkHandler) {

        fun getCharacters(): Either<Failure, List<Character>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    api.getCharacters(),
                    { it.results.map(Character.Companion::mapFromRemoteModel) },
                    emptyList()
                )
                false -> Left(NetworkConnection)
            }
        }

        fun getCharacterDetails(id: Int): Either<Failure, Character> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    api.getCharacter(id),
                    (Character.Companion::mapFromRemoteModel),
                    Character.empty
                )
                false -> Left(NetworkConnection)
            }
        }
    }

    class Local() {

    }
}
