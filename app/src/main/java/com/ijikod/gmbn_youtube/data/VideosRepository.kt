package com.ijikod.gmbn_youtube.data

import android.content.Context
import android.net.ConnectivityManager
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ijikod.gmbn_youtube.app.GMBNApplication.Companion.appContext
import com.ijikod.gmbn_youtube.data.Cache.VideoDatabase
import com.ijikod.gmbn_youtube.data.modules.CommentItems
import com.ijikod.gmbn_youtube.data.modules.Item
import com.ijikod.gmbn_youtube.data.modules.VideoItem
import com.ijikod.gmbn_youtube.data.remote.API_KEY
import com.ijikod.gmbn_youtube.data.remote.VideosApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * Video repository class to fetch data from network with [VideosApiService] or [VideoDatabase] and pass it
 * on to View Model
 * **/
class VideosRepository(private val service : VideosApiService, private val database: VideoDatabase) {


    val videoDetailsData  = MutableLiveData<List<VideoItem>>()
    val videoCommentsData = MutableLiveData<List<CommentItems>>()


    /** Fetch data from network and return is as a coroutines flow **/
    fun getVideoListResults() : Flow<PagingData<Item>>{

        val pagingSourcefactory = {
            database.videosDao().getVideos()
        }
        return Pager(
            /** Setup [PagingConfig] to determine how loading options from
             * [VideoRemoteMediator] as Data mediator **/
            config = PagingConfig(
                pageSize = MAX_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = VideoRemoteMediator(service, database),
            pagingSourceFactory = pagingSourcefactory
        ).flow
    }

    /**
     * Check for exiting data before fetch from network
     * **/
    fun getDetailsData(videoId : String){
        CoroutineScope(Dispatchers.IO).launch {
            val data = database.videoDetailDao().getVideoDetails(videoId)
            if (data.isEmpty()){
                callDetailsNetworkService(videoId)
            }else {
                videoDetailsData.postValue(data)
            }
        }
    }

    /**
     * Check for exiting comments data before fetch from network
     * **/
    fun getCommentsData(videoId : String){
        CoroutineScope(Dispatchers.IO).launch {
            val data = database.videoCommentsDao().getVideoComments(videoId)
            if (data.isEmpty()){
                callVideoCommentsService(videoId)
            }else {
                videoCommentsData.postValue(data)
            }
        }
    }

    /**
     * Fetch data from remote and save to local repository
     * **/
    @WorkerThread
    suspend fun callDetailsNetworkService(videoId: String){
        if (networkAvailable().not()){
            videoDetailsData.postValue(null)
        }else{
            val response = service.getVideoDetails(videoId, API_KEY)
            val videoDetails = response.body()?.items ?: null
            videoDetailsData.postValue(videoDetails)
            videoDetails?.let {
                database.videoDetailDao().insertAll(it)
            }
        }
    }

    /**
     * Fetch video comments data from remote anf same locally
     * **/
    @WorkerThread
    suspend fun callVideoCommentsService(videoId: String){
        if (networkAvailable().not()){
            videoCommentsData.postValue(null)
        }else {
            val response = service.getVideoComments(videoId, API_KEY)
            val comments = response.body()?.items ?: null
            videoCommentsData.postValue(comments)
            comments?.let {
                database.videoCommentsDao().insertAll(it)
            }
        }

    }

    @Suppress("DEPRECATION")
    private fun networkAvailable(): Boolean {
        // Check active internet connection
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo?.isConnectedOrConnecting ?: false
    }

    /**
     * Set max page size to avoid pager flicker
     * **/
    companion object{
        private const val MAX_PAGE_SIZE = 50
    }
}