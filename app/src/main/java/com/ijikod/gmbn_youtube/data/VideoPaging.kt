package com.ijikod.gmbn_youtube.data

import com.ijikod.gmbn_youtube.data.remote.VideosApiService
import androidx.paging.PagingSource
import com.ijikod.gmbn_youtube.data.modules.Item
import com.ijikod.gmbn_youtube.data.remote.API_KEY
import com.ijikod.gmbn_youtube.data.remote.CHANNEL_ID
import retrofit2.HttpException
import java.io.IOException


/**
 * Data Paging implementation class
 * **/
class VideoPaging (private val apiService : VideosApiService) : PagingSource<String, Item>(){

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Item> {
        /** Define initial page token to be retrieved **/
        var pageToken = params.key ?: ""

        /** Retrieve data with [VideosApiService]
         **/
        return try {
            val videoResponseData = apiService.searchVideos(channelId = CHANNEL_ID, pageToken = pageToken, key = API_KEY)
            val videoItems = videoResponseData.items
            /** Setup data to be loaded next based on [pageToken] **/
            LoadResult.Page(
                data = videoItems,
                prevKey = videoResponseData.prevPageToken,
                nextKey =  if (videoItems.isEmpty()) "" else videoResponseData.nextPageToken
            )
        }catch (exception : IOException){
            return LoadResult.Error(exception)
        }catch (exception : HttpException){
            return LoadResult.Error(exception)
        }
    }


}