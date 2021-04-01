package com.mariusbudin.sampleclean.features.episodes.presentation

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
import com.mariusbudin.sampleclean.databinding.GenericListFragmentBinding
import com.mariusbudin.sampleclean.features.episodes.data.model.Episode
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class EpisodesFragment : BaseFragment() {

    private var binding: GenericListFragmentBinding by autoCleared()
    private val viewModel: EpisodesViewModel by viewModels()

    private lateinit var adapter: EpisodesAdapter

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
        getEpisodes()
    }

    private fun setupRecyclerView() {
        adapter = EpisodesAdapter { Timber.d("Clicked episode id $it") }
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.episodes.observe(viewLifecycleOwner, ::renderEpisodes)
        viewModel.failure.observe(viewLifecycleOwner, ::handleFailure)
    }

    private fun getEpisodes() {
        binding.progress.hide()
        viewModel.getEpisodes()
    }

    private fun renderEpisodes(episodes: List<Episode>?) {
        adapter.submitList(episodes)
        binding.progress.hide()
    }

    override fun renderFailure(@StringRes message: Int) {
        binding.progress.hide()
        notifyWithAction(message, R.string.action_retry, ::getEpisodes)
    }
}