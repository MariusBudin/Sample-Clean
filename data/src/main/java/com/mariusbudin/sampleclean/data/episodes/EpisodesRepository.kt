package com.mariusbudin.sampleclean.data.episodes

import com.mariusbudin.sampleclean.data.common.BaseRepository
import com.mariusbudin.sampleclean.data.common.platform.NetworkHandler
import com.mariusbudin.sampleclean.data.episodes.model.remote.EpisodeRemoteModel
import com.mariusbudin.sampleclean.core.exception.Failure
import com.mariusbudin.sampleclean.core.exception.Failure.NetworkConnection
import com.mariusbudin.sampleclean.core.functional.Either
import com.mariusbudin.sampleclean.core.functional.Either.Left
import javax.inject.Inject

class EpisodesRepository @Inject constructor(
    private val remote: Remote,
    private val local: Local
) {
    fun getEpisodes() = remote.getEpisodes()
    fun getCharacterDetails(id: Int) = remote.getEpisodeDetails(id)

    class Remote @Inject constructor(
        private val networkHandler: NetworkHandler,
        private val api: EpisodesApi
    ) : BaseRepository.Remote(networkHandler) {

        fun getEpisodes(): Either<Failure, List<EpisodeRemoteModel>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    api.getEpisodes(),
                    { it.results },
                    emptyList()
                )
                false -> Left(NetworkConnection)
            }
        }

        fun getEpisodeDetails(id: Int): Either<Failure, EpisodeRemoteModel> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    api.getEpisode(id),
                    { it },
                    EpisodeRemoteModel.empty
                )
                false -> Left(NetworkConnection)
            }
        }
    }

    class Local @Inject constructor(
        private val dao: EpisodesDao
    ) {
        fun getEpisodes() = dao.getEpisodes()
        fun getEpisode(id: Int) = dao.getEpisode(id)
        suspend fun storeEpisodes(episodes: List<EpisodeRemoteModel>) = dao.insertAll(episodes)
        suspend fun storeEpisode(episode: EpisodeRemoteModel) = dao.insert(episode)
    }
}
