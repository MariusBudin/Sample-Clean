package com.mariusbudin.sampleclean.core.navigation

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mariusbudin.sampleclean.R

class Navigator {

    fun navigateToCharacterDetails(
        fragment: Fragment,
        characterId: Int
    ) {
//        fragment.findNavController().navigate(
//            R.id.action_charactersFragment_to_characterDetailFragment,
//            bundleOf(PARAM_ID to characterId)
//        )
    }

    companion object {
        const val PARAM_ID = "params.id"
    }
}


