package com.example.photoalbum.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photoalbum.databinding.FragmentAlbumsListBinding
import com.example.photoalbum.presentation.adapter.AlbumsListAdapter
import com.example.photoalbum.presentation.uistate.AlbumsListUIState
import com.example.photoalbum.presentation.uistate.AlbumsListViewModel
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * This class displays list of Albums
 */
@AndroidEntryPoint
class AlbumsListFragment : Fragment() {
    private lateinit var binding: FragmentAlbumsListBinding
    private val adapter = AlbumsListAdapter()

   private val viewModel: AlbumsListViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAlbumsListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.albumlist.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.albumlist.adapter =adapter
        //observe livedata from viewModel
        lifecycleScope.launchWhenCreated{
            viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
                updateUI(viewState)
            }
            viewModel.fetchAlbums()
        }
    }

    // Method to handle UI state updates
    private fun updateUI(viewState: AlbumsListUIState) {
        when (viewState) {
            is AlbumsListUIState.Content -> {
                binding.albumlist.isVisible = true
                binding.errorView.isVisible = false
                binding.loadingView.isVisible = false
                adapter.setData(viewState.newsList)
            }
            is AlbumsListUIState.Error-> {
                binding.albumlist.isVisible = false
                binding.errorView.isVisible = true
                binding.loadingView.isVisible = false
            }
           is AlbumsListUIState.Loading -> {
                binding.albumlist.isVisible = false
                binding.errorView.isVisible = false
                binding.loadingView.isVisible = true
            }
        }
    }

}