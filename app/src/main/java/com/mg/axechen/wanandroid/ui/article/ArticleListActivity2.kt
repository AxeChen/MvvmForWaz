package com.mg.axechen.wanandroid.ui.article

import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
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

class ArticleListActivity2 : BaseVMActivity<ArticleViewModel2>() {

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
        ArticleListAdapter3()
    }

    override fun setLayoutId(): Int = R.layout.activity_article_list

    override fun initData() {
        super.initData()
        intent?.run {
            showType = getStringExtra(SHOW_TYPE)
        }
        toolbarTitle.text = if (showType == ARTICLE_LIST) {
            "热门文章"
        } else {
            "热门项目"
        }
        initRecycler()
        getArticleData()
    }


    private fun initRecycler() {
        rvList.run {
            layoutManager = if (showType == ARTICLE_LIST) {
                LinearLayoutManager(this@ArticleListActivity2)
            } else {
                GridLayoutManager(this@ArticleListActivity2, 2)
            }
            adapter = listAdapter.withLoadStateFooter(PostsLoadStateAdapter(listAdapter))

        }
    }

    private fun getArticleData() {
        mViewModel.getArticleData().observe(this, Observer {
            lifecycleScope.launchWhenCreated {
                listAdapter.submitData(it)
            }
        })
    }

    override fun initView() {
        super.initView()
        mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.colorPrimary)
            .statusBarDarkFont(true, 0.2f)
            .init()
        toolbar?.run {
            setNavigationOnClickListener { finish() }

        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.no_anim, R.anim.left_to_right_out)
    }

}