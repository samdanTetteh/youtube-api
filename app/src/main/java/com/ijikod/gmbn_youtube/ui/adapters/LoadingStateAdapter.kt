package com.ijikod.gmbn_youtube.ui.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.ijikod.gmbn_youtube.ui.LoadingStateViewHolder


/**
 * Loading adapter to work with [LoadingStateViewHolder]
 * **/
class LoadingStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadingStateViewHolder>() {

    /** Populate view holder with [LoadState]
     * **/
    override fun onBindViewHolder(holder: LoadingStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    /**
     * Instantiate [LoadingStateViewHolder] with [retry] function to be invoked
     * **/
    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadingStateViewHolder {
        return LoadingStateViewHolder.create(
            parent,
            retry = retry
        )
    }

}