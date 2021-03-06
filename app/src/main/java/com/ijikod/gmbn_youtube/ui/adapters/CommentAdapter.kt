package com.ijikod.gmbn_youtube.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.ijikod.gmbn_youtube.R
import com.ijikod.gmbn_youtube.data.models.TopLevelComment

class CommentAdapter(private val comments : List<TopLevelComment>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.commment_layout_item, parent, false)
        return CommentsViewHolder(view)
    }

    override fun getItemCount(): Int = comments.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val comment = comments[position]
        // Binding data to view holder in scope function
        with(holder as CommentsViewHolder){
            bind(comment = comment)
        }
    }

    /**
     * View holder class to to display comments in comments section
     * **/
    inner class CommentsViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val authorTxt = view.findViewById<TextView>(R.id.author_txt)
        val commentTxt = view.findViewById<TextView>(R.id.comment_txt)

        fun bind(comment : TopLevelComment){
            commentTxt.text = comment.snippet.textDisplay?.let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY) }
            authorTxt.text = comment.snippet.authorDisplayName
        }
    }
}