package com.ijikod.gmbn_youtube.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.ijikod.gmbn_youtube.R
import com.ijikod.gmbn_youtube.app.GMBNApplication.Companion.appContext
import com.ijikod.gmbn_youtube.data.modules.Item
import com.ijikod.gmbn_youtube.data.modules.RemoteTokens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * BoundaryCallback class to load paginated dataset and track page token
 * **/
class VideosListBoundryCallBack (private val repository: VideosRepository) : PagedList.BoundaryCallback<Item>() {


    private var requestToken : String = ""

    private var isRequestInProgress = false

    private val _networkErrors = MutableLiveData<String>()

    val networkErrors : LiveData<String>
        get() = _networkErrors


    override fun onZeroItemsLoaded() {
        CoroutineScope(Dispatchers.IO).launch {
            fetchVideosAndSave()
        }
    }

    override fun onItemAtEndLoaded(itemAtEnd: Item) {
        CoroutineScope(Dispatchers.IO).launch {
            fetchVideosAndSave()
        }
    }


    private suspend fun fetchVideosAndSave(){
        if (repository.networkAvailable()){
            if (isRequestInProgress) return
            isRequestInProgress = true

            // Get lat token request token saved
            if (repository.getTokens().isNotEmpty()){
                repository.getTokens().last().nextToken?.let{
                    requestToken = it
                }
            }

            repository.getVideosFromNetwork(requestToken, {
                it.items?.let { items ->
                    repository.insertVideos(items){
                        val token = RemoteTokens(nextToken = it.nextPageToken)
                        // Save next token for tracking
                        repository.insertToken(token)
                        requestToken = it.nextPageToken
                        isRequestInProgress = false
                    }
                }

                _networkErrors.postValue("")
            }, { error ->
                _networkErrors.postValue(error)
                isRequestInProgress = false
            })

        }  else{
            _networkErrors.postValue(appContext.getString(R.string.internet_error_txt))
            isRequestInProgress = false
        }
    }
}