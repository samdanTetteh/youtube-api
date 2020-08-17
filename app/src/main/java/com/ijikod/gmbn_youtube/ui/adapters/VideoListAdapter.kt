package com.ijikod.gmbn_youtube.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ijikod.gmbn_youtube.R
import com.ijikod.gmbn_youtube.data.modules.Item


/**
 * Recycler Video adapter to load paged data with view holder patten
 * **/
class VideoListAdapter(private val clickListener : VideoOnclick) : PagedListAdapter<Item, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    /**
     * Get current view Item and send to view holder to bind data
     * **/
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val video = getItem(position)
        video?.let {
            (holder as VideoViewHolder).bindData(it, clickListener)
        }
    }

    /** inflate view holder view **/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return VideoViewHolder.create(
            parent
        )
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem.etag  == newItem.etag

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean =
                oldItem == newItem
        }
    }


    interface VideoOnclick{
        fun click(video: Item)
    }
}




/**
 * View holder to display recycler view list data
 * **/
class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view){
    private val image = view.findViewById<ImageView>(R.id.video_thumb)
    private val title = view.findViewById<TextView>(R.id.video_title)

    /**
     * Bind Data to list item views
     ***/
    fun bindData(video : Item, listener: VideoListAdapter.VideoOnclick){
        title.text = video.snippet.title
        Glide.with(image.context).load(video.snippet.thumbnails.medium.url).placeholder(R.mipmap.ic_launcher).into(image)

        /**
         * Navigate to details screen
         * **/
        this.itemView.setOnClickListener {
            listener.click(video)
        }
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