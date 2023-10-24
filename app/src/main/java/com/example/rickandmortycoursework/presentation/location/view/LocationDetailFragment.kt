package com.example.rickandmortycoursework.presentation.location.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmortycoursework.databinding.FragmentLocationDetailBinding
import com.example.rickandmortycoursework.presentation.location.adapter.LocationDetailAdapter
import com.example.rickandmortycoursework.presentation.location.viewModel.LocationDetailViewModel
import com.example.rickandmortycoursework.util.CalculateWindowSize
import com.example.rickandmortycoursework.util.ItemClickListener
import com.example.rickandmortycoursework.util.WindowSizeClass
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LocationDetailFragment : Fragment() {

    private var _binding: FragmentLocationDetailBinding? = null
    lateinit var binding: FragmentLocationDetailBinding
    private val locationArgs: LocationDetailFragmentArgs by navArgs()
    private val viewModel: LocationDetailViewModel by viewModels()
    private lateinit var adapter: LocationDetailAdapter
    lateinit var widthWindowClass: WindowSizeClass

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLocationDetailBinding.inflate(inflater, container, false)
        binding = _binding!!

        widthWindowClass = CalculateWindowSize(requireActivity()).calculateCurrentWidthSize()

        val isFromNavigateLocationList = locationArgs.isFromLocationList

        prepareAdapter()

        lifecycleScope.launch {
            viewModel.state.collectLatest {
                binding.viewModel = viewModel
            }
        }

        binding.imageButton.setOnClickListener {
            if (isFromNavigateLocationList) {
                navigateToLocationList()
            } else {
                findNavController().popBackStack()
            }

        }
        return binding.root
    }

    private fun navigateToLocationList() {
        val action =
            LocationDetailFragmentDirections.actionToLocationList()

        findNavController().navigate(action)
    }

    private fun prepareAdapter() {
        adapter = LocationDetailAdapter(
            ItemClickListener { characterId ->
                val action =
                    LocationDetailFragmentDirections.actionToCharacterDetail(
                        characterId
                    )
                findNavController().navigate(action)
            }
        )
        binding.recyclerView.adapter = adapter

        val spanCount = if (widthWindowClass == WindowSizeClass.EXPANDED) 3 else 2
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}