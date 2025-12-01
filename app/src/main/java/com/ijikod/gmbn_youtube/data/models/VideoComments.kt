package com.ijikod.gmbn_youtube.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore

/**
 * [Entity] class to hold video comments
 * **/
data class VideoComments (
    val kind: String,
    val etag: String,
    val nextPageToken: String,
    val pageInfo: PageInfo,
    val items: List<CommentItems>
)

data class CommentItems (
    val etag: String,
    val id: String,
    val snippet: ItemSnippet
)

data class ItemSnippet (
    var topLevelComment: TopLevelComment,
    @Ignore val canReply: Boolean,
    @Ignore val totalReplyCount: Long,
    @Ignore val isPublic: Boolean
)

@Entity(tableName = "video_comments", primaryKeys = arrayOf("etag", "id"))
data class TopLevelComment (
    val etag: String,
    val id: String,
    @Embedded
    var snippet: TopLevelCommentSnippet
)

data class TopLevelCommentSnippet (
    val videoId : String?,
    val textDisplay: String?,
    val textOriginal: String?,
    val authorDisplayName: String?,
    val authorChannelURL: String?,
    val canRate: Boolean?,
    val likeCount: Long?,
    val publishedAt: String?,
    val updatedAt: String?
)
