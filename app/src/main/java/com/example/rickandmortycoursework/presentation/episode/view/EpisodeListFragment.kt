package com.example.rickandmortycoursework.presentation.episode.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmortycoursework.databinding.FragmentEpisodeListBinding
import com.example.rickandmortycoursework.presentation.episode.adapter.EpisodeListAdapter
import com.example.rickandmortycoursework.presentation.episode.viewModel.EpisodeListViewModel
import com.example.rickandmortycoursework.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EpisodeListFragment : Fragment() {

    private var _binding: FragmentEpisodeListBinding? = null
    lateinit var binding: FragmentEpisodeListBinding
    private val viewModel: EpisodeListViewModel by viewModels()
    private lateinit var adapter: EpisodeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEpisodeListBinding.inflate(layoutInflater, container, false)
        binding = _binding!!
        prepareAdapter()

        binding.lifecycleOwner = viewLifecycleOwner

        getListData()

        lifecycleScope.launch {
            adapter.loadStateFlow.collect {
                Util.loadingState(
                    it,
                    binding.lottieAnimationView,
                    binding.refreshBtn,
                )
            }
        }

        binding.refreshBtn.setOnClickListener {
            adapter.retry()
        }

        return binding.root
    }


    private fun getListData() {
        lifecycleScope.launch {
            viewModel.getEpisodeList().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun prepareAdapter() {
        adapter = EpisodeListAdapter()
        binding.recyclerView.adapter = adapter

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager =
            GridLayoutManager(requireContext(),2)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
