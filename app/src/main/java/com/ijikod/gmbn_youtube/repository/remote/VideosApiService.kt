package com.ijikod.gmbn_youtube.repository.remote

import com.ijikod.gmbn_youtube.repository.modules.Videos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Retrofit [VideosApiService] interface defining api REST requests
 * **/
interface VideosApiService {

    /**
     * Get request to get video list from youtube search api
     * this will return a [Videos] object with list of [Videos.items]
     * **/
    @GET("search?part=snippet&order=date")
    fun searchVidoes(@Query("channelId") channelId : String, @Query("pageToken") pageToken : String, @Query("key") key : String) : Call<Videos>

}