package com.ijikod.gmbn_youtube.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedList
import com.ijikod.gmbn_youtube.Injection
import com.ijikod.gmbn_youtube.R
import com.ijikod.gmbn_youtube.databinding.FragmentListBinding
import com.ijikod.gmbn_youtube.data.models.Item
import com.ijikod.gmbn_youtube.ui.adapters.VideoListAdapter
import com.ijikod.gmbn_youtube.presentation.VideoDetailsViewModel
import com.ijikod.gmbn_youtube.presentation.VideosListViewModel

/**
 * [Fragment] class to display video list
 */
class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var adapter: VideoListAdapter
    private lateinit var viewModel : VideosListViewModel
    private lateinit var sharedViewModel : VideoDetailsViewModel

    private lateinit var listener : VideoListAdapter.VideoOnclick

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // get the view model
        viewModel = ViewModelProvider(requireActivity(), Injection.provideViewModelFactory(requireActivity()))
            .get(VideosListViewModel::class.java)

        viewModel.getNewVideos(false)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Initiate view binding
         binding = DataBindingUtil.inflate(inflater,R.layout.fragment_list, container, false)

        initAdapter()
        initRefreshViews()
        return binding.root
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


        viewModel.videos.observe(requireActivity(), Observer<PagedList<Item>> {
            Log.d("Activity", "list: ${it.size}")
            if (binding.pullRefresh.isRefreshing) binding.pullRefresh.isRefreshing = false
            binding.progressBar.visibility = View.GONE
            adapter.submitList(it)
            if (adapter.itemCount > 0) {
                binding.videoList.visibility = View.VISIBLE
            }else{
                binding.retryButton.visibility = View.GONE

            }
        })
        viewModel.networkErrors.observe(requireActivity(), Observer<String> {
            if (it.isNotEmpty()) Toast.makeText(requireContext(), "$it", Toast.LENGTH_LONG).show()

            // Show retry button when not data has been submitted
            if (adapter.itemCount == 0){
                binding.retryButton.visibility = View.VISIBLE
            }
        })
    }


    /**
     * Setup to handle refreshView actions
     * **/
    private fun initRefreshViews() {
        binding.pullRefresh.setOnRefreshListener{
            viewModel.getNewVideos(true)
        }

        binding.retryButton.setOnClickListener {
            viewModel.getNewVideos(true)
        }
    }
}
