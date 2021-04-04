package com.mariusbudin.sampleclean.presentation.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mariusbudin.sampleclean.R
import com.mariusbudin.sampleclean.presentation.characters.model.Character
import com.mariusbudin.sampleclean.core.extension.hide
import com.mariusbudin.sampleclean.presentation.common.platform.BaseFragment
import com.mariusbudin.sampleclean.core.platform.autoCleared
import com.mariusbudin.sampleclean.databinding.GenericListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharactersFragment : BaseFragment() {

    private var binding: GenericListFragmentBinding by autoCleared()
    private val viewModel: CharactersViewModel by viewModels()

    private lateinit var adapter: CharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = GenericListFragmentBinding.inflate(inflater, container, false)
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