package com.mariusbudin.sampleclean.presentation.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mariusbudin.sampleclean.domain.characters.GetCharacters
import com.mariusbudin.sampleclean.domain.characters.model.CharacterModel
import com.mariusbudin.sampleclean.presentation.characters.model.Character
import com.mariusbudin.sampleclean.presentation.characters.model.Character.Companion.mapToPresentationModel
import com.mariusbudin.sampleclean.core.domain.UseCase
import com.mariusbudin.sampleclean.core.platform.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getCharacters: GetCharacters
) : BaseViewModel() {

    private val _characters: MutableLiveData<List<Character>> = MutableLiveData()
    val characters: LiveData<List<Character>> = _characters

    fun getCharacters() {
        getCharacters(UseCase.None(), viewModelScope) {
            it.fold(
                ::handleFailure,
                ::handleCharacters
            )
        }
    }

    private fun handleCharacters(characters: List<CharacterModel>) {
        _characters.value = characters.map { it.mapToPresentationModel() }
    }
}