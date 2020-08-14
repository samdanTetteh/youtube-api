package com.ijikod.gmbn_youtube.data.modules

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class VideosData (
    val kind: String,
    val etag: String,
    val nextPageToken: String,
    val prevPageToken: String?,
    val regionCode: String,
    val pageInfo: PageInfo,
    val items: List<Item>
)

@Entity(tableName = "videos" , primaryKeys = arrayOf("kind", "etag"))
data class Item (
    val kind: String,
    val etag: String,
    @Embedded
    val id: ID,
    @Embedded
    val snippet: Snippet
)

data class ID (
    val videoId: String?
)

data class Snippet (
    val publishedAt: String,
    val channelId: String,
    val title: String,
    val description: String,
    @Embedded
    var thumbnails: Thumbnails,
    val channelTitle: String,
    val liveBroadcastContent: String,
    val publishTime: String
)

@Parcelize
data class Thumbnails (
    @Embedded(prefix = "medium_")
    @field:SerializedName("medium")
    var medium: Default,
    @Embedded(prefix = "high_")
    @field:SerializedName("high")
    var high: Default
):Parcelable

@Parcelize
data class Default (
    val url: String,
    val width: Long,
    val height: Long
):Parcelable

data class PageInfo (
    val totalResults: Long,
    val resultsPerPage: Long
)
