package com.ijikod.gmbn_youtube.presentation.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ijikod.gmbn_youtube.data.VideosRepository
import com.ijikod.gmbn_youtube.presentation.ShareViewModel
import com.ijikod.gmbn_youtube.presentation.VideoDetailsViewModel
import com.ijikod.gmbn_youtube.presentation.VideosListViewModel

/**
 * Factory for ViewModels
 * Helps with testing as well
 */
class ViewModelFactory(private val repository: VideosRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideosListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VideosListViewModel(
                repository
            ) as T
        }else if (modelClass.isAssignableFrom(VideoDetailsViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return VideoDetailsViewModel(
                repository
            ) as T
        }else if (modelClass.isAssignableFrom(ShareViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ShareViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
