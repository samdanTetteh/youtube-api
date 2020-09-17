package com.ijikod.gmbn_youtube.data

import android.content.Context
import android.net.ConnectivityManager
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.ijikod.gmbn_youtube.app.GMBNApplication
import com.ijikod.gmbn_youtube.data.Cache.VideoDatabase
import com.ijikod.gmbn_youtube.data.models.VideoItem
import com.ijikod.gmbn_youtube.data.remote.API_KEY
import com.ijikod.gmbn_youtube.data.remote.VideosApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * Repository class to enforce data single source of truth
 *
 * **/
class VideoDetailsRepository(private val service : VideosApiService, private val database: VideoDatabase){

    val videoDetailsData  = MutableLiveData<List<VideoItem>>()





    /**
     * Check for exiting data before fetch from network
     * **/
    fun getDetailsData(videoId : String){
        CoroutineScope(Dispatchers.IO).launch {
            val data = database.videoDetailDao().getVideoDetails(videoId)
            if (data.isEmpty()){
                callNetworkService(videoId)
            }else {
                videoDetailsData.postValue(data)
            }
        }
    }



    /**
     * Fetch data from remote and save to local repository
     * **/
    @WorkerThread
    suspend fun callNetworkService(videoId: String){
        if (networkAvailable().not()){
            videoDetailsData.postValue(null)
        }else{
            val response = service.getVideoDetails(videoId, API_KEY)
            val videoDetails = response.body()?.items ?: emptyList()
            videoDetailsData.postValue(videoDetails)
            database.videoDetailDao().insertAll(videoDetails)
        }
    }





    @Suppress("DEPRECATION")
    private fun networkAvailable(): Boolean {
        // Check internet connection
        val connectivityManager = GMBNApplication.appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }
}