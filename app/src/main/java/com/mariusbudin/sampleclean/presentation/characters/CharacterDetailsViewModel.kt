package com.mariusbudin.sampleclean.presentation.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mariusbudin.sampleclean.domain.characters.GetCharacterDetails
import com.mariusbudin.sampleclean.domain.characters.model.CharacterModel
import com.mariusbudin.sampleclean.presentation.characters.model.Character
import com.mariusbudin.sampleclean.presentation.characters.model.Character.Companion.mapToPresentationModel
import com.mariusbudin.sampleclean.core.platform.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterDetails: GetCharacterDetails
) : BaseViewModel() {

    private var characterId: Int? = null
    private val _characterDetails: MutableLiveData<Character> = MutableLiveData()
    val characterDetails: LiveData<Character> = _characterDetails

    fun init(characterId: Int) {
        this.characterId = characterId
    }

    fun getCharacterDetails() =
        characterId?.let { id ->
            getCharacterDetails(GetCharacterDetails.Params(id), viewModelScope) {
                it.fold(
                    ::handleFailure,
                    ::handleCharacterDetails
                )
            }
        }

    private fun handleCharacterDetails(character: CharacterModel) {
        _characterDetails.value = character.mapToPresentationModel()
    }
}