package com.ijikod.gmbn_youtube.repository.remote

import com.ijikod.gmbn_youtube.repository.modules.VideosData
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Retrofit [VideosApiService] interface defining api REST requests
 * **/
interface VideosApiService {

    /**
     * Get request to get video list from youtube search api
     * this will return a [VideosData] object with list of [VideosData.items]
     * **/
    @GET("search?part=snippet&order=date&maxResults=50")
    suspend fun searchVideos(@Query("channelId") channelId : String, @Query("pageToken") pageToken : String, @Query("key") key : String) : VideosData

}