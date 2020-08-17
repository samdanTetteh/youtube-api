package com.ijikod.gmbn_youtube.data

import android.content.Context
import android.net.ConnectivityManager
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.ijikod.gmbn_youtube.app.GMBNApplication.Companion.appContext
import com.ijikod.gmbn_youtube.data.Cache.VideoDatabase
import com.ijikod.gmbn_youtube.data.modules.*
import com.ijikod.gmbn_youtube.data.remote.API_KEY
import com.ijikod.gmbn_youtube.data.remote.CHANNEL_ID
import com.ijikod.gmbn_youtube.data.remote.VideosApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

/**
 * Video repository class to fetch data from network with [VideosApiService] or [VideoDatabase] and pass it
 * on to View Model
 * **/
class VideosRepository(private val service : VideosApiService, private val database: VideoDatabase) {


    val videoDetailsData  = MutableLiveData<List<VideoItem>>()
    val videoCommentsData = MutableLiveData<List<TopLevelComment>>()

    lateinit var videoListData : LiveData<PagedList<Item>>
    lateinit var networkErrors : LiveData<String>



    /**
     * [VideosRepository] class to load data from local repository to serve as single sauce of truth
     * **/
    fun listVideos(isRefreshing : Boolean): VideoListResults{
        if (isRefreshing && networkAvailable()){
          removeAllData()
        }

        val dataSourceFactory= getVideos()

        val videosListBoundryCallBack = VideosListBoundryCallBack(this)

        networkErrors = videosListBoundryCallBack.networkErrors

        videoListData = LivePagedListBuilder(dataSourceFactory, MAX_PAGE_SIZE).setBoundaryCallback(videosListBoundryCallBack).build()

        return VideoListResults(videoListData, networkErrors)

    }

    /**
     * Insert [videos] list from remote into local repository
     * **/
    fun insertVideos(videos: List<Item>, insertFinished: () -> Unit){
        CoroutineScope(Dispatchers.IO).launch {
            database.videosDao().insertAll(videos)
            insertFinished()
        }
    }

   private fun getVideos(): DataSource.Factory<Int, Item>{
        return database.videosDao().getVideos()
    }

    suspend fun getTokens(): List<RemoteTokens>{
        return database.remoteTokenDao().getRequestTokens()
    }


    /**
     * Insert token for page tracking
     * **/
     fun insertToken(remoteTokens: RemoteTokens){
        CoroutineScope(Dispatchers.IO).launch {
            database.remoteTokenDao().insert(remoteTokens)
        }
    }

    private fun removeAllData(){
        // Delete all video data saved locally
        deleteVideos()
        deleteTokens()
        deleteComments()
        deleteVideoDetails()
    }

    private fun deleteVideos(){
        CoroutineScope(Dispatchers.IO).launch {
            database.videosDao().deleteAllData()
        }
    }


    private fun deleteTokens(){
        CoroutineScope(Dispatchers.IO).launch {
            database.remoteTokenDao().clearRemoteTokenData()
        }
    }


    private fun deleteComments(){
        CoroutineScope(Dispatchers.IO).launch {
            database.videoCommentsDao().deleteVideoCommentsData()
        }
    }

    private fun deleteVideoDetails(){
        CoroutineScope(Dispatchers.IO).launch {
            database.videoDetailDao().deleteVideoDetailsData()
        }
    }




    @WorkerThread
    suspend fun getVideosFromNetwork(pageToken: String,
        onSuccess: (videos: VideosData) -> Unit,
        onError: (error: String) -> Unit
    ) {
        if (networkAvailable().not()){
            onError("IO Error")
        }else {
            try{
                val response = service.searchVideos(CHANNEL_ID ,pageToken, API_KEY)
                val videos = response.items ?: emptyList()
                onSuccess(response)
            }catch (exception : Exception){
                exception.message?.let { onError(it) }
            }
        }
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
            val videoDetails = response.body()?.items
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
            val comments = response.body()?.items?.map {
                 it.snippet.topLevelComment
            }
            if (comments != null) {
                response.body()?.let { database.videoCommentsDao().insertAll(comments) }
            }
            videoCommentsData.postValue(comments)
        }
    }

    /**
     * Check for active internet connection
     * **/
    @Suppress("DEPRECATION")
     fun networkAvailable(): Boolean {
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
        private const val MAX_PAGE_SIZE = 10
    }
}