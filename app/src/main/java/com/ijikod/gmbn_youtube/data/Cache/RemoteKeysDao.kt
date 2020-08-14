package com.ijikod.gmbn_youtube.data.Cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ijikod.gmbn_youtube.data.modules.RemoteTokens


/**
 * Database access object to manipulate video token data in local repository
 * **/
@Dao
interface RemoteKeysDao {

    /**
     * Select all remote tokens
     * **/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteTokens>)

    /**
     * Select all remote tokens via [keyId]
     * **/
    @Query("Select * from remote_tokens WHERE etag = :etag")
    suspend fun getKeyTokenById(etag : String) : RemoteTokens?

    /**
     * Delete all remote token data
     * **/
    @Query("DELETE from remote_tokens")
    suspend fun clearRemoteTokenData()

}