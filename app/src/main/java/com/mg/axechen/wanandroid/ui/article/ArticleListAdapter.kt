package com.mg.axechen.wanandroid.ui.article

import android.text.Html
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mg.axechen.libcommon.DensityUtil
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.model.ArticleBean

class ArticleListAdapter(data: MutableList<ArticleViewType>) :
    BaseMultiItemQuickAdapter<ArticleViewType, BaseViewHolder>(data),LoadMoreModule {

    init {
        addItemType(ArticleViewType.VIEW_TYPE_ARTICLE, R.layout.item_search_article)
        addItemType(ArticleViewType.VIEW_TYPE_PROJECT, R.layout.item_hot_project)
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
            var article = item.item as ArticleBean
            holder.setText(R.id.tvProjectTitle, article.title)
            var projectImage = holder.getView<ImageView>(R.id.ivProjectImage)
            var imageWidth = (DensityUtil.getWindowWidth(projectImage.context) - DensityUtil.dip2px(
                projectImage.context,
                64f
            )) / 2
            var lp = projectImage.layoutParams
            lp.width = imageWidth
            lp.height = imageWidth / 2

            projectImage.layoutParams = lp
            Glide.with(context).load(article.envelopePic).into(projectImage)
        }
    }
}