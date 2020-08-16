package com.ijikod.gmbn_youtube.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.ijikod.gmbn_youtube.Injection
import com.ijikod.gmbn_youtube.R
import com.ijikod.gmbn_youtube.databinding.FragmentListBinding
import com.ijikod.gmbn_youtube.data.modules.Item
import com.ijikod.gmbn_youtube.ui.adapters.LoadingStateAdapter
import com.ijikod.gmbn_youtube.ui.adapters.VideoListAdapter
import com.ijikod.gmbn_youtube.vm.VideoDetailsViewModel
import com.ijikod.gmbn_youtube.vm.VideosListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * [Fragment] class to display video list
 */
class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: VideoListAdapter
    private lateinit var viewModel : VideosListViewModel
    private lateinit var sharedViewModel : VideoDetailsViewModel

    private lateinit var listener : VideoListAdapter.VideoOnclick

    private var fetchVideosJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // get the view model
        viewModel = ViewModelProvider(requireActivity(), Injection.provideViewModelFactory(requireActivity()))
            .get(VideosListViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Initiate view binding
         binding = DataBindingUtil.inflate(inflater,R.layout.fragment_list, container, false)

        initAdapter()
        loadData()
        initRefreshView()

        return  binding.root
    }

    /**
     * Setup list adapter to load list data with paging data
     * Also to with load state footer implementation
     * **/
    private fun initAdapter(){

        /**
         * Click listener to navigate to [DetailsFragment]
         * **/
        listener = object : VideoListAdapter.VideoOnclick{
            override fun click(video: Item) {

                sharedViewModel = ViewModelProvider(requireActivity(), Injection.provideViewModelFactory(requireActivity()))
                    .get(VideoDetailsViewModel::class.java)

                sharedViewModel.setSelectedVideo(video)
                val action = ListFragmentDirections.actionListFragmentToDetailsFragment()
                findNavController().navigate(action)
            }
        }

        adapter = VideoListAdapter(listener)
        binding.videoList.adapter = adapter
        binding.videoList.adapter = adapter.withLoadStateFooter(footer = LoadingStateAdapter {
            adapter.retry()
        })


        /**
         * Show list and error message based on adaptor load state
         * **/
        adapter.addLoadStateListener {
            binding.videoList.isVisible = it.source.refresh is LoadState.NotLoading

            binding.progressBar.isVisible = it.source.refresh is LoadState.Loading

            binding.retryButton.isVisible = it.source.refresh is LoadState.Error


            val error = it.source.append as? LoadState.Error
                ?: it.source.prepend as? LoadState.Error
                ?: it.append as? LoadState.Error
                ?: it.prepend as? LoadState.Error

            error?.let {
                Toast.makeText(requireActivity(), "${it.error}", Toast.LENGTH_LONG).show()
            }

        }
    }

    /** Load initial Paging Data **/
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadData(){
        // Make sure we cancel the previous job before creating a new one
        fetchVideosJob?.cancel()
        fetchVideosJob = lifecycleScope.launch {
            binding.videoList.visibility = View.VISIBLE
            viewModel.fetchVideos().collectLatest {
                adapter.submitData(it)
            }
        }
    }


    /**
     * Setup to handle refreshView actions
     * **/
    private fun initRefreshView() {
        binding.retryButton.setOnClickListener {
            binding.videoList.scrollToPosition(0)
            adapter.retry()
        }
    }
}
