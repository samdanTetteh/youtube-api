package com.ijikod.gmbn_youtube.data.modules

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * [Entity] to  hold video token data to be saved in cache
 * **/
@Entity(tableName = "remote_tokens")
data class RemoteTokens(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val etag: String?,
    val prevToken : String?,
    val nextToken : String?
)