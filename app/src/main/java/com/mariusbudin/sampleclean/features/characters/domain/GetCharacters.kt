package com.mariusbudin.sampleclean.features.characters.domain

import com.mariusbudin.sampleclean.core.domain.UseCase
import com.mariusbudin.sampleclean.features.characters.data.CharactersRepository
import com.mariusbudin.sampleclean.features.characters.data.model.Character
import javax.inject.Inject

class GetCharacters
@Inject constructor(private val repository: CharactersRepository) :
    UseCase<List<Character>, UseCase.None>() {

    override suspend fun run(params: None) = repository.getCharacters()
}
