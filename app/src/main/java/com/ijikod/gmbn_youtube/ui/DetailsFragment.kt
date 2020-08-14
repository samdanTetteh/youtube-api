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
import androidx.recyclerview.widget.RecyclerView
import com.ijikod.gmbn_youtube.Injection
import com.ijikod.gmbn_youtube.R
import com.ijikod.gmbn_youtube.databinding.FragmentDetailsBinding
import com.ijikod.gmbn_youtube.vm.VideoDetailsViewModel
import kotlinx.android.synthetic.main.fragment_details.*

/**
 * A simple [Fragment] subclass to display movie details fragment
 */
class DetailsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var sharedViewModel : VideoDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity(), Injection
            .provideViewModelFactory(requireActivity())).get(VideoDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentDetailsBinding  = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        binding.vm = sharedViewModel
        val durationTxt = binding.durationTxt
        val viewDescTxt = binding.viewMore


        sharedViewModel.selectedVideo.value?.id?.videoId?.let { sharedViewModel.getVideoDetails(it) }
        sharedViewModel.videoDetailsData.observe(requireActivity(), Observer {
            if (it == null){
                Toast.makeText(requireActivity(), getString(R.string.internet_error_txt), Toast.LENGTH_LONG).show()
            }else{
                durationTxt.text = it[0].contentDetails.duration
                viewDescTxt.visibility  = View.VISIBLE
            }
        })


        return binding.root
    }
}