package com.ijikod.gmbn_youtube.presentation

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.ijikod.gmbn_youtube.data.VideosRepository
import com.ijikod.gmbn_youtube.data.models.Item
import com.ijikod.gmbn_youtube.data.models.VideoListResults

/**
 * ViewModel class to serve as a bridge between our repository and UI
 * With lifecycle aware capabilities [VideosRepository] with transform data from repository
 * to coroutines flow paging data to serve the UI
 * **/
class VideosListViewModel (private val repository: VideosRepository) : ViewModel() {

    private val videoLiveData = MutableLiveData<VideoListResults>()

    var videos: LiveData<PagedList<Item>> = Transformations.switchMap(videoLiveData){
        repository.videoListData
    }
    var networkErrors: LiveData<String> = Transformations.switchMap(videoLiveData){
        repository.networkErrors
    }

    fun getNewVideos(isRefreshing : Boolean){
        videoLiveData.value = repository.listVideos(isRefreshing)
    }
}