package com.mariusbudin.sampleclean.presentation.characters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import com.mariusbudin.sampleclean.presentation.MainActivity
import com.mariusbudin.sampleclean.R
import com.mariusbudin.sampleclean.presentation.characters.model.Character
import com.mariusbudin.sampleclean.core.extension.hide
import com.mariusbudin.sampleclean.core.extension.loadCircle
import com.mariusbudin.sampleclean.core.extension.show
import com.mariusbudin.sampleclean.presentation.common.navigation.Navigator.Companion.PARAM_ID
import com.mariusbudin.sampleclean.presentation.common.platform.BaseFragment
import com.mariusbudin.sampleclean.core.platform.autoCleared
import com.mariusbudin.sampleclean.databinding.CharacterDetailsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsFragment : BaseFragment() {

    private var binding: CharacterDetailsFragmentBinding by autoCleared()
    private val viewModel: CharacterDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharacterDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        arguments?.getInt(PARAM_ID)?.let { viewModel.init(it) }
        getCharacterDetails()
    }

    override fun onAttach(context: Context) {
        (activity as? MainActivity)?.hideNavigation()
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        (activity as? MainActivity)?.showNavigation()
    }

    private fun setupObservers() {
        viewModel.characterDetails.observe(viewLifecycleOwner, ::renderCharactersDetails)
        viewModel.failure.observe(viewLifecycleOwner, ::handleFailure)
    }

    private fun getCharacterDetails() {
        binding.progress.show()
        viewModel.getCharacterDetails()
    }

    private fun renderCharactersDetails(character: Character?) {
        binding.progress.hide()
        character?.let {
            binding.title.text = character.name
            binding.status.text = character.status
            binding.image.loadCircle(character.image)
        }
    }

    override fun renderFailure(@StringRes message: Int) {
        binding.progress.hide()
        notifyWithAction(message, R.string.action_retry, ::getCharacterDetails)
    }
}