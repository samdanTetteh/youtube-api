package com.ijikod.gmbn_youtube.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ijikod.gmbn_youtube.data.models.Item

/**
 * [ShareViewModel] class for sharing of data between fragments
 * **/
class ShareViewModel : ViewModel() {

    val selectedVideo: MutableLiveData<Item> = MutableLiveData()

    val videoDescription: MutableLiveData<String> = MutableLiveData()

    fun setSelectedVideo(video: Item){
        selectedVideo.value = video
    }

    fun setFullVideoDescription(desc: String){
        videoDescription.value = desc
    }
}