package com.mg.axechen.wanandroid.ui.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mg.axechen.wanandroid.databinding.ItemSearchArticleBindBinding
import com.mg.axechen.wanandroid.model.ArticleBean

class ArticleListAdapter2 : ListAdapter<ArticleBean, RecyclerView.ViewHolder>(ArticleDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ArticleViewHolder(ItemSearchArticleBindBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var articleBean = getItem(position)
        (holder as ArticleViewHolder).bind(articleBean)
    }

    class ArticleViewHolder(private val binding:ItemSearchArticleBindBinding):RecyclerView.ViewHolder(binding.root){
        init {

        }
        fun bind(item: ArticleBean) {
            binding.apply {
               article  = item
                executePendingBindings()
            }
        }
    }
}

class ArticleDiffCallBack : DiffUtil.ItemCallback<ArticleBean>() {
    override fun areItemsTheSame(oldItem: ArticleBean, newItem: ArticleBean): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ArticleBean, newItem: ArticleBean): Boolean {
        return oldItem == newItem
    }

}