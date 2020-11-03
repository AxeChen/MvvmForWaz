package com.mg.axechen.wanandroid.ui.search

import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mg.axechen.libcommon.toast
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.impl.CustomTextWatcher
import com.mg.axechen.wanandroid.base.mvvm.BaseVMActivity
import com.mg.axechen.wanandroid.model.ArticleListBean
import com.mg.axechen.wanandroid.model.HotWord
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseVMActivity<SearchViewModel>() {

    override fun setLayoutId(): Int = R.layout.activity_search

    private val data: MutableList<SearchViewType> = mutableListOf()
    private var page = 0
    private var queryText: String = ""

    private var showArticle = false

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
        }
    }

    private fun initSearchEditText() {
        editSearch.addTextChangedListener(object : CustomTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                super.onTextChanged(s, start, before, count)
                var text = s.toString()
                if (text.isEmpty()) {
                    ivSearchClose.isVisible = false
                } else {
                    ivSearchClose.isVisible = true
                }
            }
        })

        editSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                var searchText = editSearch.text.toString()
                if (searchText.isEmpty()) {
                    toast("请输入搜索的内容！")
                    return@setOnEditorActionListener false
                }
                showArticle = true
                queryText = searchText
                searchArticle(queryText)

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
        }
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
        data.add(SearchViewType(SearchViewType.VIEW_TYPE_HOT_WORD_SELECTION, ""))
        data.add(SearchViewType(SearchViewType.VIEW_TYPE_HOT_WORD, hotWords))
        listAdapter.setList(data)
        listAdapter.notifyDataSetChanged()
        listAdapter.loadMoreModule.loadMoreComplete()
        listAdapter.loadMoreModule.loadMoreEnd(true)
    }

    private fun buildHistory() {

    }

    private fun searchArticle(queryText: String) {
        if (!showArticle) {
            return
        }
        mViewModel.searchArticle(page, queryText)
    }

    private fun getHotWords() {
        mViewModel.getHotWord()
    }

}