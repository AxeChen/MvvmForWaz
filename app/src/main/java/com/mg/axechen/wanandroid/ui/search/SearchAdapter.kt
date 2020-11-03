package com.mg.axechen.wanandroid.ui.search

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.flexbox.FlexboxLayoutManager
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.model.HotWord

class SearchAdapter(data: MutableList<SearchViewType>) :
    BaseMultiItemQuickAdapter<SearchViewType, BaseViewHolder>(),LoadMoreModule {

    init {
        addItemType(SearchViewType.VIEW_TYPE_HOT_WORD_SELECTION, R.layout.item_search_selection)
        addItemType(SearchViewType.VIEW_TYPE_HOT_WORD, R.layout.item_search_hot_word_list)
        addItemType(SearchViewType.VIEW_TYPE_SEARCH_ARTICLE, R.layout.item_home_hot_article_no_image)
    }

    override fun convert(holder: BaseViewHolder, item: SearchViewType) {

        when (item.itemType) {
            SearchViewType.VIEW_TYPE_HOT_WORD -> {
                var recyclerList = holder.getView<RecyclerView>(R.id.hotWordList)
                val datas = item.item as MutableList<HotWord>
                recyclerList?.run {
                    layoutManager = FlexboxLayoutManager(recyclerList.context)
                    var hotWordAdapter = HotWordAdapter(datas)
                    adapter = hotWordAdapter
                }
            }

            SearchViewType.VIEW_TYPE_SEARCH_ARTICLE -> {

            }

            SearchViewType.VIEW_TYPE_HISTORY_SELECTION -> {

            }

            SearchViewType.VIEW_TYPE_HISTORY_ITEM -> {

            }
        }
    }

    private class HotWordAdapter(data: MutableList<HotWord>) :
        BaseQuickAdapter<HotWord, BaseViewHolder>(R.layout.item_search_hot_word, data) {
        override fun convert(holder: BaseViewHolder, item: HotWord) {
            holder.setText(R.id.tvHotWord, item.name)
        }
    }

}