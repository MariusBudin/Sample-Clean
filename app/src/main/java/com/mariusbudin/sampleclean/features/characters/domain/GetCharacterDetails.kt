package com.mariusbudin.sampleclean.features.characters.domain

import com.mariusbudin.sampleclean.core.domain.UseCase
import com.mariusbudin.sampleclean.features.characters.data.CharactersRepository
import com.mariusbudin.sampleclean.features.characters.data.model.Character
import javax.inject.Inject

class GetCharacterDetails
@Inject constructor(private val repository: CharactersRepository) :
    UseCase<Character, GetCharacterDetails.Params>() {

    override suspend fun run(params: Params) = repository.getCharacterDetails(params.id)

    data class Params(val id: Int)
}
