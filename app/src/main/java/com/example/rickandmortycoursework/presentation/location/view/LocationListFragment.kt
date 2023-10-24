package com.example.rickandmortycoursework.presentation.location.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.rickandmortycoursework.databinding.FragmentLocationListBinding
import com.example.rickandmortycoursework.presentation.location.adapter.LocationListAdapter
import com.example.rickandmortycoursework.presentation.location.viewModel.LocationListViewModel
import com.example.rickandmortycoursework.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LocationListFragment : Fragment() {

    private var _binding: FragmentLocationListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LocationListViewModel by viewModels()
    private lateinit var adapter: LocationListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentLocationListBinding.inflate(layoutInflater, container, false)

        prepareAdapter()

        binding.lifecycleOwner = viewLifecycleOwner

        getListData()

        lifecycleScope.launch {
            adapter.loadStateFlow.collect {
                Util.loadingState(
                    it,
                    binding.lottieAnimationView,
                    binding.refreshBtn
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
            viewModel.getLocationData.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun prepareAdapter() {
        adapter = LocationListAdapter()
        binding.recyclerView.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}