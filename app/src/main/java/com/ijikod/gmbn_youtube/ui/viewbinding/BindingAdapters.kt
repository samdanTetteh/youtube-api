package com.ijikod.gmbn_youtube.ui.viewbinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.ijikod.gmbn_youtube.R


/**
 *  A [BindingAdapter] that uses the Glide library to load the video image.
 */
@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, image : String?){
    image.let {
        Glide.with(imageView.context).load(it).placeholder(R.mipmap.ic_launcher).into(imageView)
    }
}