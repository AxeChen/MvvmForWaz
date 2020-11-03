package com.mg.axechen.wanandroid.ui.search

import android.service.autofill.FillEventHistory
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mg.axechen.libcommon.KeyBoardUtil
import com.mg.axechen.libcommon.toast
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.impl.CustomTextWatcher
import com.mg.axechen.wanandroid.base.mvvm.BaseVMActivity
import com.mg.axechen.wanandroid.cache.AppCache
import com.mg.axechen.wanandroid.model.ArticleListBean
import com.mg.axechen.wanandroid.model.HotWord
import kotlinx.android.synthetic.main.activity_search.*
import java.lang.Exception
import java.util.*

class SearchActivity : BaseVMActivity<SearchViewModel>() {

    override fun setLayoutId(): Int = R.layout.activity_search

    private val data: MutableList<SearchViewType> = mutableListOf()
    private val historySearch: MutableList<String> = mutableListOf()
    private val mHotWords: MutableList<HotWord> = mutableListOf()
    private var page = 0
    private var queryText: String = ""
    private var showArticle = false
    private var gson = Gson()

    private val listAdapter by lazy {
        SearchAdapter(data)
    }

    override fun initView() {
        super.initView()
        mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.white)
            .statusBarDarkFont(true, 0.2f)
            .init()
        toolBar?.run {
            setNavigationOnClickListener { finish() }
        }
        initSearchEditText()
        initRecycler()
        ivSearchClose.setOnClickListener {
            showArticle = false
            editSearch.setText("")
            getHotWords()
        }
    }

    private fun initSearchEditText() {
        editSearch.addTextChangedListener(object : CustomTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                super.onTextChanged(s, start, before, count)
                var text = s.toString()
                ivSearchClose.isVisible = text.isNotEmpty()
            }
        })

        editSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                var searchText = editSearch.text.toString()
                if (searchText.isEmpty()) {
                    toast("请输入搜索的内容！")
                    return@setOnEditorActionListener false
                }
                startSearch(searchText)
            }
            false
        }
    }

    private fun initRecycler() {
        rvSearchList?.run {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = listAdapter
            listAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = true
            listAdapter.loadMoreModule.setOnLoadMoreListener {
                page++
                searchArticle(queryText)
            }
            listAdapter.apply {
                flowItemClickListener = object : SearchAdapter.FlowItemClickListener {
                    override fun itemClick(item: String) {
                        page = 0
                        editSearch.setText(queryText)
                        startSearch(item)
                    }
                }
            }
        }
    }

    private fun startSearch(item: String) {
        searchArticle(item)
        queryText = item
        addHistory(queryText)
        showArticle = true
        KeyBoardUtil.hideSoftInput(this, editSearch)
    }

    override fun initData() {
        super.initData()
        getHotWords()
        startObserver()
    }

    private fun startObserver() {
        mViewModel.run {
            uiState.observe(this@SearchActivity, Observer {
                it.hotWord?.run {
                    buildHotWord(this)
                }
                it.articleListBean?.run {
                    buildArticles(this)
                }
            })
        }
    }

    private fun buildArticles(articleListBean: ArticleListBean) {
        if (articleListBean.datas.isEmpty()) {
            if (page == 0) {
                //显示没有数据的页面
            } else {
                // 显示没有更多的数据
                listAdapter.loadMoreModule.loadMoreComplete()
                listAdapter.loadMoreModule.loadMoreEnd(false)
            }
        } else {
            if (page == 0) {
                // 清除掉历史记录和其他
                data.clear()
            }
            articleListBean.datas.forEach {
                data.add(SearchViewType(SearchViewType.VIEW_TYPE_SEARCH_ARTICLE, it))
            }
            listAdapter.setList(data)
            listAdapter.notifyDataSetChanged()
        }
    }

    private fun buildHotWord(hotWords: MutableList<HotWord>) {
        if (hotWords.isNotEmpty()) {
            mHotWords.addAll(hotWords)
            data.add(SearchViewType(SearchViewType.VIEW_TYPE_HOT_WORD_SELECTION, ""))
            data.add(SearchViewType(SearchViewType.VIEW_TYPE_HOT_WORD, hotWords))
        }
        buildHistory()
    }

    private fun addHistory(history: String) {
        var historyList = getHistoryList()
        if (historyList == null) {
            historyList = mutableListOf()
        }
        historyList.run {
            if (this.contains(history)) {
                remove(history)
            }
            add(history)
        }
        // 最多十条数据
        if (historyList.size > 10) {
            historyList.removeAt(historyList.size - 1)
        }

        saveHistory(gson.toJson(historyList))
    }

    private fun removeHistory(history: String) {
        var historyList = getHistoryList()
        historyList?.run {
            if (this.contains(history)) {
                remove(history)
            }
        }
        saveHistory(gson.toJson(historyList))
    }

    private fun cleanHistory() {
        AppCache.searchHistoryCache = ""
    }

    private fun saveHistory(historyString: String) {
        AppCache.searchHistoryCache = historyString
    }

    private fun getHistoryList(): MutableList<String>? {
        var searchHistory = AppCache.searchHistoryCache
        if (searchHistory.isNotEmpty()) {
            try {
                var historySearch: MutableList<String> =
                    gson.fromJson(searchHistory, object : TypeToken<MutableList<String>>() {}.type)
                return historySearch
            } catch (e: Exception) {
                return null
            }
        }
        return null
    }

    private fun buildHistory() {
        var searchHistory = getHistoryList()
        searchHistory?.run {
            if (isNotEmpty()) {
                data.add(SearchViewType(SearchViewType.VIEW_TYPE_HISTORY_SELECTION, ""))
                data.add(SearchViewType(SearchViewType.VIEW_TYPE_HISTORY_ITEM, this))
            }
        }
        listAdapter.setList(data)
        listAdapter.notifyDataSetChanged()
        listAdapter.loadMoreModule.loadMoreComplete()
        listAdapter.loadMoreModule.loadMoreEnd(true)
    }

    private fun searchArticle(queryText: String) {
        if (!showArticle) {
            return
        }
        mViewModel.searchArticle(page, queryText)
    }

    private fun getHotWords() {
        if (mHotWords.isEmpty()) {
            mViewModel.getHotWord()
        } else {
            data.clear()
            data.add(SearchViewType(SearchViewType.VIEW_TYPE_HOT_WORD_SELECTION, ""))
            data.add(SearchViewType(SearchViewType.VIEW_TYPE_HOT_WORD, mHotWords))
            buildHistory()
        }
    }

}