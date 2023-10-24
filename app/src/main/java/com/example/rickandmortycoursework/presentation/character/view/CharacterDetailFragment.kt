package com.example.rickandmortycoursework.presentation.character.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmortycoursework.databinding.FragmentCharacterDetailBinding
import com.example.rickandmortycoursework.presentation.character.viewmodel.CharacterDetailViewModel
import com.example.rickandmortycoursework.presentation.episode.adapter.EpisodeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CharacterDetailViewModel
    private val adapter: EpisodeAdapter by lazy { EpisodeAdapter() }

    override
    fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCharacterDetailBinding.inflate(layoutInflater, container, false)

        viewModel = ViewModelProvider(this)[CharacterDetailViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.recyclerViewEpisode.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewEpisode.adapter = adapter

        binding.locationGroup.setOnClickListener {
            val locationUrl = viewModel.getLocationUrl()

            locationUrl?.let {
                val locationId = (locationUrl.split("/"))[5].toInt()
                viewModel.setNavigateLocationId(locationId)

                if (viewModel.getNavigationLocationID() != null) {

                    navigateToLocationDetail(viewModel.getNavigationLocationID()!!)
                    viewModel.displayDetailComplete()
                }
            }
        }

        binding.imageButton.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

    private fun navigateToLocationDetail(locationID: Int) {
        val action =
            CharacterDetailFragmentDirections.actionToLocationDetail(
                locationID,
                false
            )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}