package com.mariusbudin.sampleclean.domain.episodes

import com.mariusbudin.sampleclean.data.episodes.EpisodesRepository
import com.mariusbudin.sampleclean.domain.episodes.model.EpisodeModel
import com.mariusbudin.sampleclean.domain.episodes.model.EpisodeModel.Companion.mapToDomainModel
import com.mariusbudin.sampleclean.core.domain.UseCase
import com.mariusbudin.sampleclean.core.functional.map
import javax.inject.Inject

class GetEpisodes
@Inject constructor(private val repository: EpisodesRepository) :
    UseCase<List<EpisodeModel>, UseCase.None>() {

    override suspend fun run(params: None) =
        repository.getEpisodes().map { it.map { it.mapToDomainModel() } }
}
