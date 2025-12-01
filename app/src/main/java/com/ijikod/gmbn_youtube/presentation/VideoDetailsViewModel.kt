package com.ijikod.gmbn_youtube.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ijikod.gmbn_youtube.data.VideosRepository
import com.ijikod.gmbn_youtube.data.models.Item
import com.ijikod.gmbn_youtube.data.models.VideoItem


/**
 * Shared View Model class serving as a bridge between repository anf UI
 * **/
class VideoDetailsViewModel(private val repository: VideosRepository) : ViewModel() {

    // Details data emitted from repository
     val videoDetailsData = repository.videoDetailsData

    val videoCommentsData = repository.videoCommentsData

    /**
     * View Model function to retrieve video comments data based on [videoId]
     * from server or cache
     * **/
    fun getVideoComments(videoId: String){
        repository.getCommentsData(videoId)
    }

    /**
     * View Model function to retrieve video details data based on [videoId]
     * from server or cache
     * **/
    fun getVideoDetails(videoId : String){
        repository.getDetailsData(videoId)
    }


}