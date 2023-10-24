package com.example.rickandmortycoursework.presentation.character.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ccom.example.rickandmortycoursework.presentation.character.viewmodel.states.ListType
import com.example.rickandmortycoursework.databinding.DialogViewBinding
import com.example.rickandmortycoursework.databinding.FragmentCharacterListBinding
import com.example.rickandmortycoursework.domain.model.CharactersDomain
import com.example.rickandmortycoursework.R
import com.example.rickandmortycoursework.presentation.character.adapter.CharacterAdapter
import com.example.rickandmortycoursework.util.CalculateWindowSize
import com.example.rickandmortycoursework.util.Util
import com.example.rickandmortycoursework.util.WindowSizeClass
import com.example.rickandmortycoursework.presentation.character.viewmodel.CharacterViewModel
import com.example.rickandmortycoursework.util.ItemLongClickListener


import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private lateinit var binding: FragmentCharacterListBinding
    lateinit var viewModel: CharacterViewModel
    private lateinit var characterAdapter: CharacterAdapter
    lateinit var widthWindowClass: WindowSizeClass
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentCharacterListBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[CharacterViewModel::class.java]

        widthWindowClass = CalculateWindowSize(requireActivity()).calculateCurrentWidthSize()

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        prepareCharacterAdapter()
        setCharacterListLayoutManager()

        viewModel.checkIfTheFilterHasBeenApplied()

        getListData()

        binding.refreshBtn.setOnClickListener {
            characterAdapter.retry()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            characterAdapter.loadStateFlow.collect {
                val isListEmpty =
                    it.refresh is LoadState.Error && characterAdapter.itemCount == 0
                Util.loadingState(
                    it,
                    binding.lottieAnimationView,
                    binding.refreshBtn,
                    isListEmpty,
                    binding.filterErrorMessage,
                    viewModel.checkIfTheFilterHasBeenApplied()
                )
            }
        }

        return binding.root
    }


    private fun setCharacterListLayoutManager() {

        binding.characterList.layoutManager = GridLayoutManager(requireContext(), 2)
        characterAdapter.setListType(ListType.GridLayout)
    }

    private fun getListData() {
        lifecycleScope.launch {
            viewModel.getListData().collectLatest {
                characterAdapter.submitData(it)
            }
        }
    }

    private fun prepareCharacterAdapter() {
        characterAdapter = CharacterAdapter(
            ItemLongClickListener {
                showAlertDialog(it)
            }
        )
        setCharacterListLayoutManager()
        characterAdapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        binding.characterList.adapter = characterAdapter

    }

    private fun showAlertDialog(charactersDomain: CharactersDomain) {

        viewModel.getAllFavoriteCharacters()
        val dialogView = DialogViewBinding.inflate(LayoutInflater.from(requireContext()))

        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView.root)
            .create()


        val isHasAddedCharacter = viewModel.isHasAddedCharacter(charactersDomain)

        setDialogText(isHasAddedCharacter, dialogView, charactersDomain)

        alertDialog.show()

        dialogView.btnYes.setOnClickListener {

            if (isHasAddedCharacter) {
                viewModel.deleteCharacterFromMyFavoriteList(charactersDomain)
                alertDialog.cancel()

            } else {
                charactersDomain.setFavoriteState(true)
                viewModel.insertMyFavoriteList(charactersDomain)
                alertDialog.cancel()
            }
            showToastMessage()
            viewModel.doneToastMessage()
        }

        dialogView.btnNo.setOnClickListener {
            alertDialog.cancel()
        }

    }

    private fun showToastMessage() {
        if (viewModel.getIsShowToastMessage()) {
            Toast.makeText(
                requireContext(),
                viewModel.getToastMessage(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setDialogText(
        isHasAddedCharacter: Boolean,
        dialogView: DialogViewBinding,
        charactersDomain: CharactersDomain,
    ) {
        if (isHasAddedCharacter) {
            dialogView.txtHeader.text = getString(R.string.dialog_header_remove_favorite)
            dialogView.txtQuestion.text =
                getString(R.string.dialog_question_remove_character_favorite, charactersDomain.name)

        } else {
            dialogView.txtHeader.text = getString(R.string.dialog_header_add_favorite)
            dialogView.txtQuestion.text =
                getString(R.string.dialog_question_add_character_favorite, charactersDomain.name)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageButton.setOnClickListener {
            val action = CharacterListFragmentDirections.actionCharacterListFragmentToFilterDialog()
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun onStart() {
        super.onStart()

    }
}
