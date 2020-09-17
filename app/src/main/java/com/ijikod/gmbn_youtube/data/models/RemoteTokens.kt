package com.ijikod.gmbn_youtube.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * [Entity] to  hold video token data to be saved in cache
 * **/
@Entity(tableName = "remote_tokens")
data class RemoteTokens(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nextToken : String?
)