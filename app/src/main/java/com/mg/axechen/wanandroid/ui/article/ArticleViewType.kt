package com.mg.axechen.wanandroid.ui.article

import com.chad.library.adapter.base.entity.MultiItemEntity

class ArticleViewType(var item: Any, override val itemType: Int) : MultiItemEntity {

    companion object {
        const val VIEW_TYPE_ARTICLE = 1
        const val VIEW_TYPE_PROJECT = 2
    }
}