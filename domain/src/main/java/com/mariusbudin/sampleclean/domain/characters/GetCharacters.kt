package com.mariusbudin.sampleclean.domain.characters

import com.mariusbudin.sampleclean.data.characters.CharactersRepository
import com.mariusbudin.sampleclean.domain.characters.model.CharacterModel
import com.mariusbudin.sampleclean.domain.characters.model.CharacterModel.Companion.mapToDomainModel
import com.mariusbudin.sampleclean.core.domain.UseCase
import com.mariusbudin.sampleclean.core.functional.map
import javax.inject.Inject

class GetCharacters
@Inject constructor(private val repository: CharactersRepository) :
    UseCase<List<CharacterModel>, UseCase.None>() {

    override suspend fun run(params: None) =
        repository.getCharacters().map { it.map { it.mapToDomainModel() } }
}
