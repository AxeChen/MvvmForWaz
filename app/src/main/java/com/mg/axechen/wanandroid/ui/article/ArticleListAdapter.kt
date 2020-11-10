package com.mg.axechen.wanandroid.ui.article

import android.text.Html
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.model.ArticleBean

class ArticleListAdapter(data: MutableList<ArticleViewType>) :
    BaseMultiItemQuickAdapter<ArticleViewType, BaseViewHolder>(data) {

    init {
        addItemType(ArticleViewType.VIEW_TYPE_ARTICLE, R.layout.item_search_article)
        addItemType(ArticleViewType.VIEW_TYPE_PROJECT, R.layout.item_search_article)
    }

    override fun convert(holder: BaseViewHolder, item: ArticleViewType) {
        if(item.itemType == ArticleViewType.VIEW_TYPE_ARTICLE){
            var article = item.item as ArticleBean
            holder.setText(R.id.tvHotArticleTitle, Html.fromHtml(article.title))
            holder.setText(R.id.tvHotArticleTime, article.niceDate)
            val ivItemImage = holder.getView<ImageView>(R.id.ivItemImage)
            if (article.envelopePic.isEmpty()) {
                holder.setGone(R.id.ivItemImage, true)
            } else {
                holder.setGone(R.id.ivItemImage, false)
                Glide.with(context).load(article.envelopePic).into(ivItemImage)
            }
        }else{

        }
    }
}