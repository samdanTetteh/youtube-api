package com.ijikod.gmbn_youtube.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ijikod.gmbn_youtube.R
import com.ijikod.gmbn_youtube.repository.modules.Item


/**
 * Recycler Video adapter to load paged data with view holder patten
 * **/
class ViewListAdapter :PagingDataAdapter<Item, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    /**
     * Get current view Item and send to view holder to bind data
     * **/
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val video = getItem(position)
        video?.let {
            (holder as VideoViewHolder).bindData(it)
        }
    }

    /** inflate view holder view **/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VideoViewHolder.create(parent)
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem.etag  == newItem.etag

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem == newItem
        }
    }
}




/**
 * View holder to display recycler view list data
 * **/
class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view){
    private val image = view.findViewById<ImageView>(R.id.video_thumb)
    private val title = view.findViewById<TextView>(R.id.video_title)


    init {
        /**
         * Navigate to details screen
         * **/
        view.setOnClickListener{

        }
    }

    /**
     * Bind Data to list item views
     ***/
    fun bindData(video : Item){
        title.text = video.snippet.title
        Glide.with(image.context).load(video.snippet.thumbnails.medium.url).placeholder(R.mipmap.ic_launcher).into(image)
    }


    companion object {
        /**
         * Singleton access to [VideoViewHolder] returning inflated view
         * **/
        fun create(parent: ViewGroup): VideoViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.video_list_item, parent, false)
            return VideoViewHolder(view)
        }
    }
}