package com.ijikod.gmbn_youtube.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ijikod.gmbn_youtube.data.VideosRepository
import com.ijikod.gmbn_youtube.vm.VideoDetailsViewModel
import com.ijikod.gmbn_youtube.vm.VideosListViewModel

/**
 * Factory for ViewModels
 * Helps with testing as well
 */
class ViewModelFactory(private val repository: VideosRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideosListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VideosListViewModel(repository) as T
        }else if (modelClass.isAssignableFrom(VideoDetailsViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return VideoDetailsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
