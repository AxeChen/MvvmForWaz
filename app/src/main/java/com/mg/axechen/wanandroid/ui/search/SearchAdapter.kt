package com.mg.axechen.wanandroid.ui.search

import android.text.Html
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.flexbox.FlexboxLayoutManager
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.model.ArticleBean
import com.mg.axechen.wanandroid.model.HotWord

class SearchAdapter(data: MutableList<SearchViewType>) :
    BaseMultiItemQuickAdapter<SearchViewType, BaseViewHolder>(data), LoadMoreModule {

    var flowItemClickListener: FlowItemClickListener? = null

    init {
        addItemType(SearchViewType.VIEW_TYPE_HOT_WORD_SELECTION, R.layout.item_search_selection)
        addItemType(SearchViewType.VIEW_TYPE_HOT_WORD, R.layout.item_search_hot_word_list)
        addItemType(SearchViewType.VIEW_TYPE_SEARCH_ARTICLE, R.layout.item_search_article)
        addItemType(SearchViewType.VIEW_TYPE_HISTORY_ITEM, R.layout.item_search_hot_word_list)
        addItemType(
            SearchViewType.VIEW_TYPE_HISTORY_SELECTION,
            R.layout.item_search_history_selection
        )
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
                    hotWordAdapter.setOnItemClickListener { adapter, view, position ->
                        flowItemClickListener?.itemClick(datas[position].name)
                    }
                }
            }

            SearchViewType.VIEW_TYPE_SEARCH_ARTICLE -> {
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
            }

            SearchViewType.VIEW_TYPE_HISTORY_SELECTION -> {

            }

            SearchViewType.VIEW_TYPE_HISTORY_ITEM -> {
                var recyclerList = holder.getView<RecyclerView>(R.id.hotWordList)
                val datas = item.item as MutableList<String>
                recyclerList?.run {
                    layoutManager = FlexboxLayoutManager(recyclerList.context)
                    var historyAdapter = HistoryAdapter(datas)
                    adapter = historyAdapter
                    historyAdapter.setOnItemClickListener { adapter, view, position ->
                        flowItemClickListener?.itemClick(datas[position])
                    }
                }
            }
        }
    }

    private class HistoryAdapter(data: MutableList<String>) :
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_search_hot_word, data) {
        override fun convert(holder: BaseViewHolder, item: String) {
            holder.setText(R.id.tvHotWord, item)
        }

    }

    private class HotWordAdapter(data: MutableList<HotWord>) :
        BaseQuickAdapter<HotWord, BaseViewHolder>(R.layout.item_search_hot_word, data) {
        override fun convert(holder: BaseViewHolder, item: HotWord) {
            holder.setText(R.id.tvHotWord, item.name)
        }
    }

    interface FlowItemClickListener {
        fun itemClick(item: String)
    }

}