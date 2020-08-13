package com.ijikod.gmbn_youtube.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ijikod.gmbn_youtube.repository.modules.Item
import com.ijikod.gmbn_youtube.repository.remote.VideosApiService
import kotlinx.coroutines.flow.Flow

/**
 * Video repository class to fetch data from network with [VideosApiService] and pass it
 * on to viewmodel
 * **/
class VideosRepository(private val service : VideosApiService) {

    /** Fetch data from network and return is as a coroutines flow **/
    fun getVideoListResults() : Flow<PagingData<Item>>{
        return Pager(
            /** Setup [PagingConfig] to determine how loading options from
             * [VideoPaging] as paging source factory **/
            config = PagingConfig(
                pageSize = MAX_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {VideoPaging(service)}
        ).flow
    }


    /**
     * Set max page size to avoid pager flicker
     * **/
    companion object{
        private const val MAX_PAGE_SIZE = 50
    }
}