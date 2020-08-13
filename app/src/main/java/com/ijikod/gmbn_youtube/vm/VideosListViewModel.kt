package com.ijikod.gmbn_youtube.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ijikod.gmbn_youtube.repository.VideosRepository
import com.ijikod.gmbn_youtube.repository.modules.Item
import kotlinx.coroutines.flow.Flow

/**
 * ViewModel class to serve as a bridge between our repository and UI
 * With lifecycle aware capabilities [VideosRepository] with transform data from repository
 * to coroutines flow paging data to serve the UI
 * **/
class VideosListViewModel (private val repository: VideosRepository) : ViewModel() {

    private var currentResults : Flow<PagingData<Item>>? = null

    /**
     * fetch videos data and save results with [viewModelScope]
     * **/
    fun fetchVideos() : Flow<PagingData<Item>>{
        val newResult : Flow<PagingData<Item>> = repository.getVideoListResults()
            .cachedIn(viewModelScope)
        currentResults = newResult
        return newResult
    }
}