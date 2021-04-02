package com.mariusbudin.sampleclean.episodes.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import com.mariusbudin.sampleclean.core.di.AppModule
import com.mariusbudin.sampleclean.core.extension.hide
import com.mariusbudin.sampleclean.core.platform.BaseFragment
import com.mariusbudin.sampleclean.core.platform.NetworkHandler
import com.mariusbudin.sampleclean.core.platform.autoCleared
import com.mariusbudin.sampleclean.databinding.GenericListFragmentBinding
import com.mariusbudin.sampleclean.episodes.data.EpisodesApi
import com.mariusbudin.sampleclean.episodes.data.EpisodesRepository
import com.mariusbudin.sampleclean.episodes.data.model.Episode
import com.mariusbudin.sampleclean.episodes.domain.GetEpisodes
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

class EpisodesFragment : BaseFragment() {

    private var binding: GenericListFragmentBinding by autoCleared()
    private lateinit var viewModel: EpisodesViewModel

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

        //FIXME remove this and use injection, testing to see if the issue is with Hilt
        /////////////////
        viewModel = EpisodesViewModel(
            GetEpisodes(
                EpisodesRepository(
                    EpisodesRepository.Remote(
                        NetworkHandler(requireContext()), EpisodesApi.Service(
                            Retrofit.Builder()
                                .baseUrl(AppModule.API_BASE_URL)
                                .client(AppModule.createClient())
                                .addConverterFactory(MoshiConverterFactory.create())
                                .build()
                        )
                    ), EpisodesRepository.Local()
                )
            )
        )
        /////////////////

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
//        notifyWithAction(message, R.string.action_retry, ::getEpisodes)
    }

    companion object {
        fun newInstance() = EpisodesFragment()
    }
}