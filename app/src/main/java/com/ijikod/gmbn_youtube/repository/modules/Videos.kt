package com.ijikod.gmbn_youtube.repository.modules
/**
 * Data Module to hold [Videos] list data
 * **/
data class Videos (
    val kind: String,
    val etag: String,
    val nextPageToken: String,
    val prevPageToken: String,
    val regionCode: String,
    val pageInfo: PageInfo,
    val items: List<Item>
)

data class Item (
    val kind: String,
    val etag: String,
    val id: ID,
    val snippet: Snippet
)

data class ID (
    val kind: String,
    val videoID: String
)

data class Snippet (
    val publishedAt: String,
    val channelID: String,
    val title: String,
    val description: String,
    val thumbnails: Thumbnails,
    val channelTitle: String,
    val liveBroadcastContent: String,
    val publishTime: String
)

data class Thumbnails (
    val default: Default,
    val medium: Default,
    val high: Default
)

data class Default (
    val url: String,
    val width: Long,
    val height: Long
)

data class PageInfo (
    val totalResults: Long,
    val resultsPerPage: Long
)