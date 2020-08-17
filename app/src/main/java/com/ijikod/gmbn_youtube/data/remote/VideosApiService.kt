package com.ijikod.gmbn_youtube.data.remote

import com.ijikod.gmbn_youtube.data.modules.VideoComments
import com.ijikod.gmbn_youtube.data.modules.VideoDetailsData
import com.ijikod.gmbn_youtube.data.modules.VideosData
import retrofit2.Call
import retrofit2.Response
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
    @GET("search?part=snippet&order=date&maxResults=10")
    suspend fun searchVideos(@Query("channelId") channelId : String, @Query("pageToken") pageToken : String, @Query("key") key : String) : VideosData

    /**
     * Get request to retrieve video details such as duration based on [id] for details screen
     * **/
    @GET("videos?part=snippet,contentDetails")
    suspend fun getVideoDetails(@Query("id") id : String, @Query("key") key : String) : Response<VideoDetailsData>

    /**
     * Get request to retrieve video comments base on [id]
     * **/
    @GET("commentThreads?part=snippet&order=time")
    suspend fun getVideoComments(@Query("videoId") id : String, @Query("key") key : String) : Response<VideoComments>

}