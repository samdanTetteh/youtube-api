package com.ijikod.gmbn_youtube.data.Cache

import androidx.room.*
import com.ijikod.gmbn_youtube.data.modules.CommentItems
import com.ijikod.gmbn_youtube.data.modules.TopLevelComment
import com.ijikod.gmbn_youtube.data.modules.VideoComments

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
    suspend fun insertAll(videosComments : List<TopLevelComment>)


    /**
     * Fetch items from local database
     * **/
    @Query("Select * from video_comments where videoId = :id")
    fun getVideoComments(id: String): List<TopLevelComment>


    /**
     * Delete all data
     * **/
    @Query("Delete from video_comments")
    suspend fun deleteVideoDetailsData()
}