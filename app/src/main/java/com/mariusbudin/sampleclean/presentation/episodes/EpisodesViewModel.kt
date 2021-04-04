package com.mariusbudin.sampleclean.presentation.episodes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mariusbudin.sampleclean.domain.episodes.GetEpisodes
import com.mariusbudin.sampleclean.domain.episodes.model.EpisodeModel
import com.mariusbudin.sampleclean.core.domain.UseCase
import com.mariusbudin.sampleclean.core.platform.BaseViewModel
import com.mariusbudin.sampleclean.presentation.episodes.model.Episode
import com.mariusbudin.sampleclean.presentation.episodes.model.Episode.Companion.mapToPresentationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val getEpisodes: GetEpisodes
) : BaseViewModel() {

    private val _episodes: MutableLiveData<List<Episode>> = MutableLiveData()
    val episodes: LiveData<List<Episode>> = _episodes

    fun getEpisodes() {
        getEpisodes(UseCase.None(), viewModelScope) {
            it.fold(
                ::handleFailure,
                ::handleEpisodes
            )
        }
    }

    private fun handleEpisodes(episodes: List<EpisodeModel>) {
        _episodes.value = episodes.map { it.mapToPresentationModel() }
    }
}