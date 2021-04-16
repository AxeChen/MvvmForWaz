package com.mg.axechen.wanandroid.ui.article

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mg.axechen.wanandroid.databinding.ItemHotProjectBindBinding
import com.mg.axechen.wanandroid.databinding.ItemSearchArticleBindBinding
import com.mg.axechen.wanandroid.model.ArticleBean

class ArticleListAdapter3 :
    ListAdapter<ArticleViewType, RecyclerView.ViewHolder>(ArticleDiffCallBack()) {

    var onItemClick: ItemClick? = null

    override fun getItemViewType(position: Int): Int {
        return getItem(position).itemType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            ArticleViewType.VIEW_TYPE_ARTICLE -> {
                ArticleViewHolder(
                    ItemSearchArticleBindBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            ArticleViewType.VIEW_TYPE_PROJECT -> {
                ProjectViewHolder(
                    ItemHotProjectBindBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                ArticleViewHolder(
                    ItemSearchArticleBindBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var viewType = getItemViewType(position)
        when (viewType) {
            ArticleViewType.VIEW_TYPE_ARTICLE -> {
                var articleHolder = holder as ArticleViewHolder
                var articleBean = getItem(position)!!.item as ArticleBean
                articleHolder.bind(articleBean)
                articleHolder.itemView.setOnClickListener {
                    onItemClick?.onClicked(it, articleBean)
                }
            }

            ArticleViewType.VIEW_TYPE_PROJECT -> {
                var articleHolder = holder as ProjectViewHolder
                var articleBean = getItem(position)!!.item as ArticleBean
                articleHolder.bind(articleBean)
                articleHolder.itemView.setOnClickListener {
                    onItemClick?.onClicked(it, articleBean)
                }
            }
        }


    }

    class ProjectViewHolder(val binding: ItemHotProjectBindBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ArticleBean) {
            binding.apply {
                article = item
                executePendingBindings()
            }
        }
    }

    class ArticleViewHolder(val binding: ItemSearchArticleBindBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ArticleBean) {
            binding.apply {
                article = item
                executePendingBindings()
            }
        }
    }

    class ArticleDiffCallBack : DiffUtil.ItemCallback<ArticleViewType>() {
        override fun areItemsTheSame(oldItem: ArticleViewType, newItem: ArticleViewType): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ArticleViewType,
            newItem: ArticleViewType
        ): Boolean {
            return oldItem == newItem
        }

    }

    public interface ItemClick {
        fun onClicked(view: View, articleBean: ArticleBean)
    }

}

