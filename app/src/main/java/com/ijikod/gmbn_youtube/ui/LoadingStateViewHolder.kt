package com.ijikod.gmbn_youtube.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.ijikod.gmbn_youtube.R
import com.ijikod.gmbn_youtube.databinding.FooterViewItemBinding


/**
 * View holder to display loading state in recycler view footer
 * **/
class LoadingStateViewHolder(private val binding: FooterViewItemBinding, retry: () -> Unit): RecyclerView.ViewHolder(binding.root) {


    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    /**
     * Bind load state to view holder views
     * **/
    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState !is LoadState.Loading
        binding.errorMsg.isVisible = loadState !is LoadState.Loading
    }

    /**
     * Singleton [create] method to be called from adapter class
     * **/
    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): LoadingStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.footer_view_item, parent, false)
            val binding = FooterViewItemBinding.bind(view)
            return LoadingStateViewHolder(binding, retry)
        }
    }


}