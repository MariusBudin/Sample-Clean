package com.mariusbudin.sampleclean.features.episodes.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mariusbudin.sampleclean.core.domain.UseCase
import com.mariusbudin.sampleclean.core.platform.BaseViewModel
import com.mariusbudin.sampleclean.features.episodes.data.model.Episode
import com.mariusbudin.sampleclean.features.episodes.domain.GetEpisodes
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

    private fun handleEpisodes(episodes: List<Episode>) {
        _episodes.value = episodes
    }
}