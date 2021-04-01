package com.mariusbudin.sampleclean.features.episodes.data

import com.mariusbudin.sampleclean.core.data.BaseRepository
import com.mariusbudin.sampleclean.core.exception.Failure
import com.mariusbudin.sampleclean.core.exception.Failure.NetworkConnection
import com.mariusbudin.sampleclean.core.functional.Either
import com.mariusbudin.sampleclean.core.functional.Either.Left
import com.mariusbudin.sampleclean.core.platform.NetworkHandler
import com.mariusbudin.sampleclean.features.episodes.data.model.Episode
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

        fun getEpisodes(): Either<Failure, List<Episode>> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    api.getEpisodes(),
                    { it.results.map(Episode.Companion::mapFromRemoteModel) },
                    emptyList()
                )
                false -> Left(NetworkConnection)
            }
        }

        fun getEpisodeDetails(id: Int): Either<Failure, Episode> {
            return when (networkHandler.isNetworkAvailable()) {
                true -> request(
                    api.getEpisode(id),
                    (Episode.Companion::mapFromRemoteModel),
                    Episode.empty
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
        suspend fun storeEpisodes(episodes: List<Episode>) = dao.insertAll(episodes)
        suspend fun storeEpisode(episode: Episode) = dao.insert(episode)
    }
}
