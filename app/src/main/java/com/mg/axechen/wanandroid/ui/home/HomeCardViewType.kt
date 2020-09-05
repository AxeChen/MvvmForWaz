package com.mg.axechen.wanandroid.ui.home

import com.chad.library.adapter.base.entity.MultiItemEntity

class HomeCardViewType(var item: Any, override val itemType: Int) : MultiItemEntity {

    companion object {
        const val VIEW_TYPE_HOT_ARTICLE = 1
        const val VIEW_TYPE_HOT_PROJECT = 2
        const val VIEW_TYPE_BANNER = 2
        const val VIEW_CARD_REWARD = 3
        const val VIEW_CARD_PLAY_STAR = 4
        const val VIEW_CARD_BANNER = 5
    }
}