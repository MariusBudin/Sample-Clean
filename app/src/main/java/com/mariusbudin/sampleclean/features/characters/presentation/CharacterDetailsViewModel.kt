package com.mariusbudin.sampleclean.features.characters.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mariusbudin.sampleclean.core.platform.BaseViewModel
import com.mariusbudin.sampleclean.features.characters.data.model.Character
import com.mariusbudin.sampleclean.features.characters.domain.GetCharacterDetails
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

    private fun handleCharacterDetails(character: Character) {
        _characterDetails.value = character
    }
}