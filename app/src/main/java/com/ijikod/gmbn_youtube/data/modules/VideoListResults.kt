package com.ijikod.gmbn_youtube.data.modules

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class VideoListResults(val data: LiveData<PagedList<Item>>, val networkErrors : LiveData<String>)