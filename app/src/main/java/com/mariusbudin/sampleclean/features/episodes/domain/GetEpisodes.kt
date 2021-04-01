package com.mariusbudin.sampleclean.features.episodes.domain

import com.mariusbudin.sampleclean.core.domain.UseCase
import com.mariusbudin.sampleclean.features.episodes.data.EpisodesRepository
import com.mariusbudin.sampleclean.features.episodes.data.model.Episode
import javax.inject.Inject

class GetEpisodes
@Inject constructor(private val repository: EpisodesRepository) :
    UseCase<List<Episode>, UseCase.None>() {

    override suspend fun run(params: None) = repository.getEpisodes()
}
