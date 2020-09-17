package com.ijikod.gmbn_youtube.ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ijikod.gmbn_youtube.Injection
import com.ijikod.gmbn_youtube.R
import com.ijikod.gmbn_youtube.databinding.FragmentDecsriptionBinding
import com.ijikod.gmbn_youtube.presentation.ShareViewModel


/**
 * A simple [Fragment] subclass to show video description
 */
class DescriptionFragment : Fragment() {

    private lateinit var sharedViewModel: ShareViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity(), Injection.provideViewModelFactory(requireActivity()))
            .get(ShareViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding  = FragmentDecsriptionBinding.inflate(inflater, container, false)

        loadInitially(binding)
        return binding.root
    }

   // Load initial data
    private fun loadInitially(binding: FragmentDecsriptionBinding){
        val descText = binding.descMsg

        sharedViewModel.videoDescription.observe(viewLifecycleOwner, Observer {
            descText.text = it
        })
    }
}