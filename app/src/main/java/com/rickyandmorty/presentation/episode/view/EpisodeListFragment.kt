package com.rickyandmorty.presentation.episode.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rickyandmorty.databinding.FragmentEpisodeListBinding
import com.rickyandmorty.presentation.episode.adapter.EpisodeListAdapter
import com.rickyandmorty.presentation.episode.viewModel.EpisodeListViewModel
import com.rickyandmorty.util.ItemClickListener
import com.rickyandmorty.util.Util
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EpisodeListFragment : Fragment() {

    private var _binding: FragmentEpisodeListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EpisodeListViewModel by viewModels()
    private lateinit var adapter: EpisodeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEpisodeListBinding.inflate(layoutInflater, container, false)

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
        adapter = EpisodeListAdapter(
            ItemClickListener {
                navigateToEpisodeDetail(it)
            }
        )
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun navigateToEpisodeDetail(id: Int) {
        val action = EpisodeListFragmentDirections.actionToEpisodeDetail(id)
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