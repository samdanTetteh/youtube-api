package com.ijikod.gmbn_youtube.data.modules

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.ijikod.gmbn_youtube.data.models.Item

data class VideoListResults(val data: LiveData<PagedList<Item>>, val networkErrors : LiveData<String>)