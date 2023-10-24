package com.example.rickandmortycoursework.presentation.episode.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rickandmortycoursework.databinding.FragmentEpisodeDetailBinding
import com.example.rickandmortycoursework.presentation.episode.viewModel.EpisodeDetailViewModel
import com.example.rickandmortycoursework.presentation.location.adapter.LocationDetailAdapter
import com.example.rickandmortycoursework.util.CalculateWindowSize
import com.example.rickandmortycoursework.util.ItemClickListener
import com.example.rickandmortycoursework.util.WindowSizeClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeDetailFragment : Fragment() {

    private var _binding: FragmentEpisodeDetailBinding? = null
    private val binding get() = _binding!!
    private val episodeDetailArgs: EpisodeDetailFragmentArgs by navArgs()
    private val viewModel: EpisodeDetailViewModel by viewModels()
    lateinit var widthWindowClass: WindowSizeClass

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEpisodeDetailBinding.inflate(layoutInflater, container, false)

        widthWindowClass = CalculateWindowSize(requireActivity()).calculateCurrentWidthSize()

        binding.lifecycleOwner = viewLifecycleOwner
        /*val episodeId = */episodeDetailArgs.episodeId

        binding.viewModel = viewModel

        prepareAdapter()

        binding.imageButton.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }


    private fun prepareAdapter() {
        val adapter = LocationDetailAdapter(
            ItemClickListener { characterId ->
                navigateToCharacterDetail(characterId)
            }
        )
        binding.recyclerView.adapter = adapter
        val spanCount = if (widthWindowClass == WindowSizeClass.EXPANDED) 3 else 2
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
    }

    private fun navigateToCharacterDetail(characterId: Int) {
        val action = EpisodeDetailFragmentDirections.actionToCharacterDetailFragment(
            characterId
        )
        findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
