package com.ijikod.gmbn_youtube.data.Cache

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ijikod.gmbn_youtube.data.modules.Item

/**
 * Database Access Object to retrieve locally saved data
 * **/
@Dao
interface VideosDao {

    /**
     *
     * Insert all items into local database
     ***/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(videos : List<Item>)

    /**
     * Fetch all items from local database
     * **/
    @Query("Select * from videos")
    fun getVideos(): DataSource.Factory<Int, Item>

    /**
     * Delete all data
     * **/
    @Query("Delete from videos")
    suspend fun deleteAllData()

}