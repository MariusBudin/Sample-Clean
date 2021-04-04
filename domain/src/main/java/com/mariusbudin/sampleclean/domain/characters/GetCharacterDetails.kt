package com.mariusbudin.sampleclean.domain.characters

import com.mariusbudin.sampleclean.data.characters.CharactersRepository
import com.mariusbudin.sampleclean.domain.characters.model.CharacterModel
import com.mariusbudin.sampleclean.domain.characters.model.CharacterModel.Companion.mapToDomainModel
import com.mariusbudin.sampleclean.core.domain.UseCase
import com.mariusbudin.sampleclean.core.functional.map
import javax.inject.Inject

class GetCharacterDetails
@Inject constructor(private val repository: CharactersRepository) :
    UseCase<CharacterModel, GetCharacterDetails.Params>() {

    override suspend fun run(params: Params) =
        repository.getCharacterDetails(params.id).map { it.mapToDomainModel() }

    data class Params(val id: Int)
}
