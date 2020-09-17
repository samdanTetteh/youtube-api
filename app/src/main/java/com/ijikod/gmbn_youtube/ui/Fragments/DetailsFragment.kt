package com.ijikod.gmbn_youtube.ui.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.ijikod.gmbn_youtube.Injection
import com.ijikod.gmbn_youtube.R
import com.ijikod.gmbn_youtube.databinding.FragmentDetailsBinding
import com.ijikod.gmbn_youtube.presentation.ShareViewModel
import com.ijikod.gmbn_youtube.ui.adapters.CommentAdapter
import com.ijikod.gmbn_youtube.presentation.VideoDetailsViewModel


/**
 * A simple [Fragment] subclass to display movie details fragment
 */
class DetailsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var videoDetailsViewModel : VideoDetailsViewModel
    private lateinit var shareViewModel: ShareViewModel
    lateinit var adapter: CommentAdapter
    lateinit var durationTxt : TextView
    lateinit var viewMoreTxt : TextView
    lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        videoDetailsViewModel = ViewModelProvider(requireActivity(), Injection
            .provideViewModelFactory(requireActivity())).get(VideoDetailsViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding  = FragmentDetailsBinding.inflate(inflater, container, false)
        // Use data binding to bind data to views

        initPage(binding)
        setInitialData(binding)
        loadDetailsData()
        loadVideoComments()
        return binding.root
    }

    // show initially stored data
    private fun setInitialData(binding : FragmentDetailsBinding){
        shareViewModel = ViewModelProvider(requireActivity(), Injection
            .provideViewModelFactory(requireActivity())).get(ShareViewModel::class.java)
        binding.vm = shareViewModel
    }

    private fun initPage(binding : FragmentDetailsBinding){
        //Initialise layout Views
        durationTxt = binding.durationTxt
        viewMoreTxt = binding.viewMore
        recyclerView = binding.commentsList
        progressBar = binding.progressBar
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }



    private fun loadDetailsData(){
        // Call function to load data from repository
        shareViewModel.selectedVideo.value?.id?.videoId?.let { videoDetailsViewModel.getVideoDetails(it) }

        // Subscribe to listen on object changes from view model using live data
        videoDetailsViewModel.videoDetailsData.observe(viewLifecycleOwner, Observer { videoItemList ->
            if (videoItemList == null){
                Toast.makeText(requireActivity(), getString(R.string.internet_error_txt), Toast.LENGTH_LONG).show()
                viewMoreTxt.visibility  = View.INVISIBLE
                durationTxt.text = getString(R.string.no_data_txt)
            }else{
                durationTxt.text = durationFormatting(videoItemList[0].contentDetails.duration)
                viewMoreTxt.visibility  = View.VISIBLE
                // Move to details fragment to show full description details
                viewMoreTxt.setOnClickListener {
//                    videoDetailsViewModel.setSelectedVideoItem(videoItemList[0])
                    shareViewModel.setFullVideoDescription(videoItemList[0].snippet.description)
                    findNavController().navigate(DetailsFragmentDirections.actionDetailsFragmentToDescriptionFragment())
                }
            }
        })
    }


    private fun loadVideoComments(){
        // Call function to load data from repository
        shareViewModel.selectedVideo.value?.id?.videoId?.let { videoDetailsViewModel.getVideoComments(it) }

        // Subscribe to listen on object changes from view model using live data
        videoDetailsViewModel.videoCommentsData.observe(viewLifecycleOwner, Observer {
            if (it == null){
                hideCommentsList()
                Toast.makeText(requireActivity(), getString(R.string.internet_error_txt), Toast.LENGTH_LONG).show()
            }else{
                adapter = CommentAdapter(it)
                recyclerView.adapter = adapter
                showCommentsList()
            }
        })
    }



    private fun showCommentsList(){
        progressBar.visibility= View.INVISIBLE
        recyclerView.visibility = View.VISIBLE
    }

    private fun hideCommentsList(){
        progressBar.visibility= View.INVISIBLE
        recyclerView.visibility = View.INVISIBLE
    }



     fun durationFormatting(duration: String) : String{
        // Format duration text
        return duration.let {
            it.trim().substring(2, it.length).replace("M", getString(R.string.minute_txt), ignoreCase = false)
        }.run {
            replace("S", getString(R.string.seconds_txt), ignoreCase = false)
        }
    }
}