package com.ijikod.gmbn_youtube.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ijikod.gmbn_youtube.data.Cache.VideoDatabase
import com.ijikod.gmbn_youtube.data.modules.Item
import com.ijikod.gmbn_youtube.data.modules.RemoteTokens
import com.ijikod.gmbn_youtube.data.remote.API_KEY
import com.ijikod.gmbn_youtube.data.remote.CHANNEL_ID
import com.ijikod.gmbn_youtube.data.remote.VideosApiService
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException


/** Mediator class to fetch from remote with
 * [VideosApiService] and save remote data locally
 * to [VideoDatabase] **/
@OptIn(ExperimentalPagingApi::class)
class VideoRemoteMediator(private val service: VideosApiService, private val database: VideoDatabase) : RemoteMediator<Int, Item>(){

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Item>
    ): MediatorResult {

        val page = when (loadType){
            //Add new data from remote
            LoadType.REFRESH -> {
                val remoteTokens = getRemoteTokenClosestToCurrentPosition(state)
                remoteTokens?.nextToken ?: ""
            }

            LoadType.PREPEND -> {
                val remoteTokens = getRemoteTokenForFirstItem(state)
                    ?: // The LoadType is PREPEND so some data was loaded before,
                    // so we should have been able to get remote tokens
                    // If the remoteTokens are null, then we're an invalid state and we have a bug
                    throw InvalidObjectException("Remote Token and the prevKey should not be null")
                // If the previous key is null, then we can't request more data
                 remoteTokens.prevToken
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
            }

            // Add data to the end of list
            LoadType.APPEND -> {
                val remoteTokens = getRemoteTokenForLastItem(state)
                if (remoteTokens?.nextToken == null) {
                    throw InvalidObjectException("Token should not be null for $loadType")
                }
                remoteTokens.nextToken
            }

        }


        try {
            //Load initial data from remote
            val apiResponse = service.searchVideos(CHANNEL_ID, page, API_KEY)

            val videos = apiResponse.items
            val endOfPaginationReached = videos.isEmpty()

            database.withTransaction {
                //Clear all tables in all databases
                if (loadType == LoadType.REFRESH){
                    database.remoteTokenDao().clearRemoteTokenData()
                    database.videosDao().deleteAllData()
                }

                val prevKey = apiResponse.prevPageToken
                val nextKey = if (endOfPaginationReached) null else apiResponse.nextPageToken

                // Group all tokens to the saved
                val keys = videos.map {
                    RemoteTokens(etag = it.etag, prevToken = prevKey, nextToken = nextKey)
                }

                database.videosDao().insertAll(videos)
                database.remoteTokenDao().insertAll(keys)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch (exception : IOException){
            return MediatorResult.Error(exception)
        }catch (exception : HttpException){
            return MediatorResult.Error(exception)
        }

    }

    private suspend fun getRemoteTokenForLastItem(state: PagingState<Int, Item>): RemoteTokens? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                // Get the remote tokens of the last item retrieved
                database.remoteTokenDao().getKeyTokenById(repo.etag)
            }
    }

    private suspend fun getRemoteTokenClosestToCurrentPosition(
        state: PagingState<Int, Item>
    ): RemoteTokens? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.etag?.let { etag ->
                database.remoteTokenDao().getKeyTokenById(etag)
            }
        }
    }

    private suspend fun getRemoteTokenForFirstItem(state: PagingState<Int, Item>): RemoteTokens? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let {
                // Get the remote tokens of the first items retrieved
                database.remoteTokenDao().getKeyTokenById(it.etag)
            }
    }
}