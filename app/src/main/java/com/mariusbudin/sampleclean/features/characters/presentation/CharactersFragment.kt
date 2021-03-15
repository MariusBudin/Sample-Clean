package com.mariusbudin.sampleclean.features.characters.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mariusbudin.sampleclean.R
import com.mariusbudin.sampleclean.core.extension.hide
import com.mariusbudin.sampleclean.core.platform.BaseFragment
import com.mariusbudin.sampleclean.core.platform.autoCleared
import com.mariusbudin.sampleclean.databinding.CharactersFragmentBinding
import com.mariusbudin.sampleclean.features.characters.data.model.Character
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : BaseFragment() {

    private var binding: CharactersFragmentBinding by autoCleared()
    private val viewModel: CharactersViewModel by viewModels()

    private lateinit var adapter: CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CharactersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        getCharacters()
    }

    private fun setupRecyclerView() {
        adapter = CharactersAdapter { navigator.navigateToCharacterDetails(this, it) }
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.characters.observe(viewLifecycleOwner, ::renderCharacters)
        viewModel.failure.observe(viewLifecycleOwner, ::handleFailure)
    }

    private fun getCharacters() {
        binding.progress.hide()
        viewModel.getCharacters()
    }

    private fun renderCharacters(characters: List<Character>?) {
        adapter.submitList(characters)
        binding.progress.hide()
    }

    override fun renderFailure(@StringRes message: Int) {
        binding.progress.hide()
        notifyWithAction(message, R.string.action_retry, ::getCharacters)
    }
}