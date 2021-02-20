package com.mg.axechen.wanandroid.ui.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mg.axechen.wanandroid.databinding.BottomLoadingBinding

class PostsLoadStateAdapter(private val adapter: ArticleListAdapter3) :
    LoadStateAdapter<PostsLoadStateAdapter.BottomLoding>() {


    class BottomLoding(private val binding: BottomLoadingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {

        }

        fun bind(item: LoadState) {
            binding.apply {
                loadState = item
                executePendingBindings()
            }
        }
    }

    override fun onBindViewHolder(holder: BottomLoding, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): BottomLoding {
        return BottomLoding(
            BottomLoadingBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
        )
    }
}