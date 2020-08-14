package com.ijikod.gmbn_youtube.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import com.ijikod.gmbn_youtube.R
import com.ijikod.gmbn_youtube.data.modules.CommentItems
import com.ijikod.gmbn_youtube.data.modules.VideoComments
import kotlinx.android.synthetic.main.commment_layout_item.view.*

class CommentAdapter(private val comments : List<CommentItems>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


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

        fun bind(comment : CommentItems){
            commentTxt.text = comment.snippet.topLevelComment.snippet.textDisplay
            authorTxt.text = comment.snippet.topLevelComment.snippet.authorDisplayName
        }

    }
}