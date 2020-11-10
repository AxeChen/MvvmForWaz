package com.mg.axechen.wanandroid.ui.article

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeremyliao.liveeventbus.LiveEventBus
import com.mg.axechen.libcommon.startKtxActivity
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.mvvm.BaseVMActivity
import com.mg.axechen.wanandroid.base.webview.WebViewActivity
import com.mg.axechen.wanandroid.model.ArticleBean
import com.mg.axechen.wanandroid.ui.home.ArticleViewModel
import com.mg.axechen.wanandroid.ui.search.SearchViewType
import kotlinx.android.synthetic.main.activity_article_list.*
import kotlinx.android.synthetic.main.layout_toolbar.*

class ArticleListActivity : BaseVMActivity<ArticleViewModel>() {

    // 文章
    private var articleList: MutableList<ArticleBean> = mutableListOf()

    // 项目
    private var projectList: MutableList<ArticleBean> = mutableListOf()

    private var articleViews: MutableList<ArticleViewType> = mutableListOf()

    private var showType = ARTICLE_LIST

    private var page = 1

    companion object {
        const val ARTICLE_LIST_EVENT = "article_list_event"

        // 文章list
        const val ARTICLE_LIST = "article_list"

        // 项目list
        const val PROJECT_LIST = "project_list"
        const val SHOW_TYPE = "show_type"
    }

    private val listAdapter by lazy {
        ArticleListAdapter(articleViews)
    }

    override fun setLayoutId(): Int = R.layout.activity_article_list

    override fun initData() {
        super.initData()
        intent?.run {
            showType = getStringExtra(SHOW_TYPE)
        }
        initRecycler()
        initEvent()
    }

    private fun initEvent() {
        LiveEventBus.get(ARTICLE_LIST, MutableList::class.java).observe(this, Observer {
            if (it.isNotEmpty()) {
                articleList.addAll(it as MutableList<ArticleBean>)
                articleList.forEach {
                    articleViews.add(ArticleViewType(it, ArticleViewType.VIEW_TYPE_ARTICLE))
                }
                listAdapter.setList(articleViews)
                listAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun initRecycler() {
        rvList.run {
            layoutManager = if (showType == ARTICLE_LIST) {
                LinearLayoutManager(this@ArticleListActivity)
            } else {
                GridLayoutManager(this@ArticleListActivity, 2)
            }
            adapter = listAdapter
            listAdapter.setOnItemClickListener { adapter, view, position ->
                var viewType = articleViews[position]
                if (viewType.itemType == ArticleViewType.VIEW_TYPE_ARTICLE) {
                    var articleBean = viewType.item as ArticleBean
                    startKtxActivity<WebViewActivity>(
                        values = mutableListOf(
                            WebViewActivity.TITLE to articleBean.title,
                            WebViewActivity.URL to articleBean.link
                        )
                    )
                }
            }
            listAdapter.loadMoreModule.setOnLoadMoreListener {
                page++

            }
        }
    }

    override fun initView() {
        super.initView()
        mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.colorPrimary)
            .statusBarDarkFont(true, 0.2f)
            .init()
        toolbar?.run {
            setNavigationOnClickListener { finish() }
            toolbarTitle.text = if (showType == ARTICLE_LIST) {
                "热门文章"
            } else {
                "热门项目"
            }
        }
    }


}