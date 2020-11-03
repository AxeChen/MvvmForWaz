package com.mg.axechen.wanandroid.ui.search

import com.chad.library.adapter.base.entity.MultiItemEntity

class SearchViewType(override val itemType: Int, var item: Any) : MultiItemEntity {

    companion object {
        // 文章item
        const val VIEW_TYPE_SEARCH_ARTICLE = 0

        // 热词selection
        const val VIEW_TYPE_HOT_WORD_SELECTION = 1

        // 历史记录selection
        const val VIEW_TYPE_HISTORY_SELECTION = 2

        // 历史记录item
        const val VIEW_TYPE_HISTORY_ITEM = 3

        const val VIEW_TYPE_HOT_WORD = 4
    }
}