package com.ijikod.gmbn_youtube.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ijikod.gmbn_youtube.data.VideosRepository
import com.ijikod.gmbn_youtube.data.models.Item
import com.ijikod.gmbn_youtube.data.models.VideoItem


/**
 * Shared View Model class serving as a bridge between repository anf UI
 * **/
class VideoDetailsViewModel(private val repository: VideosRepository) : ViewModel() {

     // Shared video item between details and list fragment
    val selectedVideo : MutableLiveData<Item> = MutableLiveData<Item>()

    /**
     * Shared [VideoItem] to display video details
     * **/
    val selectedVideoItem : MutableLiveData<VideoItem> = MutableLiveData<VideoItem>()

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

    /**
     * Set value to Shared video [Item] used in Details View
     * **/
     fun setSelectedVideo(video : Item){
        selectedVideo.value = video
    }

    /**
     * Set value to Shared [VideoItem] used in Details View
     * **/
    fun setSelectedVideoItem(video : VideoItem){
        selectedVideoItem.value = video
    }

}