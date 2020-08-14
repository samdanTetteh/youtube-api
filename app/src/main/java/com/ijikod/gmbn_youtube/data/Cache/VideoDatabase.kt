package com.ijikod.gmbn_youtube.data.Cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ijikod.gmbn_youtube.data.modules.*

/**
 * Room Database initialisation to save data locally
 * **/
@Database(entities = arrayOf(Item::class, RemoteTokens::class, VideoItem::class, CommentItems::class), version = 1, exportSchema = false)
@TypeConverters(RequestConverters::class)
abstract class VideoDatabase : RoomDatabase() {

    /** [VideosDao] instance  **/
    abstract fun videosDao() : VideosDao

    /**
     * [RemoteKeysDao] instance for local data manipulation
     * **/
    abstract fun remoteTokenDao() : RemoteKeysDao

    /**
     * [VideoDetailDao] instance for local data manipulation
     * **/
    abstract fun videoDetailDao() : VideoDetailDao


    /**
     *  [VideoCommentsDao] instance for local data manipulation
     * **/
    abstract fun videoCommentsDao() : VideoCommentsDao



    /**
     * Database singleton access
     * **/
    companion object{
        @Volatile
        private var INSTANCE: VideoDatabase? = null

        fun getInstance(context: Context): VideoDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                VideoDatabase::class.java, "GMBN.db")
                .build()
    }

}