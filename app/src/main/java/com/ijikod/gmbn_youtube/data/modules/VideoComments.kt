package com.ijikod.gmbn_youtube.data.modules

import androidx.room.Embedded
import androidx.room.Entity

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

@Entity(tableName = "video_comments")
data class CommentItems (
    val etag: String,
    val id: String,
    @Embedded(prefix = "comment_")
    val snippet: ItemSnippet
)

data class ItemSnippet (
    @Embedded
    val topLevelComment: TopLevelComment,
    val canReply: Boolean,
    val totalReplyCount: Long,
    val isPublic: Boolean
)

data class TopLevelComment (
    val etag: String,
    val id: String,
    @Embedded(prefix = "top_level_comment_")
    val snippet: TopLevelCommentSnippet
)

data class TopLevelCommentSnippet (
    val textDisplay: String,
    val textOriginal: String,
    val authorDisplayName: String,
    val authorProfileImageURL: String,
    val authorChannelURL: String,
    @Embedded
    val authorChannelID: AuthorChannelID,
    val canRate: Boolean,
    val likeCount: Long,
    val publishedAt: String,
    val updatedAt: String
)

data class AuthorChannelID (
    val value: String
)
