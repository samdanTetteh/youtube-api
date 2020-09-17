package com.ijikod.gmbn_youtube.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.ijikod.gmbn_youtube.Injection
import com.ijikod.gmbn_youtube.R
import com.ijikod.gmbn_youtube.databinding.FragmentDecsriptionBinding
import com.ijikod.gmbn_youtube.presentation.VideoDetailsViewModel


/**
 * A simple [Fragment] subclass to show video description
 */
class DescriptionFragment : Fragment() {

    lateinit var sharedViewModel: VideoDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = ViewModelProvider(requireActivity(), Injection.provideViewModelFactory(requireActivity()))
            .get(VideoDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding : FragmentDecsriptionBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_decsription, container, false)
        binding.vm = sharedViewModel

        return binding.root
    }
}