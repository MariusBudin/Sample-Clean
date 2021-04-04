package com.mariusbudin.sampleclean.data.characters

import com.mariusbudin.sampleclean.data.characters.model.remote.CharacterRemoteModel
import com.mariusbudin.sampleclean.data.common.BaseRepository
import com.mariusbudin.sampleclean.data.common.platform.NetworkHandler
import com.mariusbudin.sampleclean.core.exception.Failure
import com.mariusbudin.sampleclean.core.exception.Failure.NetworkConnection
import com.mariusbudin.sampleclean.core.functional.Either
import com.mariusbudin.sampleclean.core.functional.Either.Left
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

        fun getCharacters(): Either<Failure, List<CharacterRemoteModel>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    api.getCharacters(),
                    { it.results },
                    emptyList()
                )
                false -> Left(NetworkConnection)
            }
        }

        fun getCharacterDetails(id: Int): Either<Failure, CharacterRemoteModel> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    api.getCharacter(id),
                    { it },
                    CharacterRemoteModel.empty
                )
                false -> Left(NetworkConnection)
            }
        }
    }

    class Local @Inject constructor(
        private val dao: CharactersDao
    ) {
        fun getCharacters() = dao.getCharacters()
        fun getCharacter(id: Int) = dao.getCharacter(id)
        suspend fun storeCharacters(characters: List<CharacterRemoteModel>) =
            dao.insertAll(characters)

        suspend fun storeCharacter(character: CharacterRemoteModel) = dao.insert(character)
    }
}
