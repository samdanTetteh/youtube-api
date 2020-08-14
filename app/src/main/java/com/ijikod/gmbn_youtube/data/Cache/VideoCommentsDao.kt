package com.ijikod.gmbn_youtube.data.Cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ijikod.gmbn_youtube.data.modules.CommentItems
import com.ijikod.gmbn_youtube.data.modules.VideoComments
import com.ijikod.gmbn_youtube.data.modules.VideoItem

/**
 * Data access object for local video comments data manipulation
 * **/
@Dao
interface VideoCommentsDao {

    /**
     *
     * Insert all video detail item into local database
     ***/
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(videos : List<CommentItems>)


    /**
     * Fetch items from local database
     * **/
    @Query("Select * from video_comments where id = :id")
    fun getVideoComments(id: String): List<CommentItems>


    /**
     * Delete all data
     * **/
    @Query("Delete from video_comments")
    suspend fun deleteVideoDetailsData()
}