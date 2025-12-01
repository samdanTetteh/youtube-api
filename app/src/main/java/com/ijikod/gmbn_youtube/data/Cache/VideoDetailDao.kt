package com.ijikod.gmbn_youtube.data.Cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ijikod.gmbn_youtube.data.models.VideoItem

/**
 * Database Access Object to retrieve and insert data into database
 * **/
@Dao
interface VideoDetailDao {

    /**
     *
     * Insert all video detail item into local database
     ***/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(videos : List<VideoItem>)


    /**
     * Fetch items from local database
     * **/
    @Query("Select * from video_details_data where id = :id")
    fun getVideoDetails(id: String): List<VideoItem>


    /**
     * Delete all data
     * **/
    @Query("Delete from video_details_data")
    suspend fun deleteVideoDetailsData()

}