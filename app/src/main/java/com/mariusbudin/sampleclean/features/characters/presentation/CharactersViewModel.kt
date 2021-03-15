package com.mariusbudin.sampleclean.features.characters.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mariusbudin.sampleclean.core.domain.UseCase
import com.mariusbudin.sampleclean.core.platform.BaseViewModel
import com.mariusbudin.sampleclean.features.characters.data.model.Character
import com.mariusbudin.sampleclean.features.characters.domain.GetCharacters
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

    private fun handleCharacters(characters: List<Character>) {
        _characters.value = characters
    }
}