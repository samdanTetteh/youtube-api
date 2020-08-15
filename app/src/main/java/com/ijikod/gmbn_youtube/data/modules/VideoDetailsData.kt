package com.ijikod.gmbn_youtube.data.modules

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class VideoDetailsData (
    val kind: String,
    val etag: String,
    val items: List<VideoItem>
)

@Entity(tableName = "video_details_data", primaryKeys = arrayOf("kind", "etag"))
data class VideoItem (
    val kind: String,
    val etag: String,
    val id: String,
    @Embedded
    val snippet: Snippet,
    @Embedded
    val contentDetails: ContentDetails
)


data class ContentDetails (
    val duration: String,
    val dimension: String,
    val definition: String,
    val caption: String,
    val licensedContent: Boolean,
    val projection: String
)

@Parcelize
data class Localized (
    val title: String,
    val description: String
):Parcelable
