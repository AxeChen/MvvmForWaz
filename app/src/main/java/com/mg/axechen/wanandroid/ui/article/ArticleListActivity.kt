package com.mg.axechen.wanandroid.ui.article

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeremyliao.liveeventbus.LiveEventBus
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.mvvm.BaseVMActivity
import com.mg.axechen.wanandroid.model.ArticleBean
import com.mg.axechen.wanandroid.ui.home.ArticleViewModel
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
        initEvent()
        startObserver()
    }

    private fun startObserver() {
        mViewModel.run {
            uiState.observe(this@ArticleListActivity, Observer {
                it.articleList?.run {
                    if (this.size > 0) {
                        this.forEach {
                            articleViews.add(ArticleViewType(it, ArticleViewType.VIEW_TYPE_ARTICLE))
                        }
                        listAdapter.submitList(articleViews)
                        listAdapter.notifyDataSetChanged()
                    } else {
//                        listAdapter.loadMoreModule.loadMoreComplete()
//                        listAdapter.loadMoreModule.loadMoreEnd(false)
                    }
                }

                it.projectList?.run {
                    if (this.size > 0) {
                        this.forEach {
                            articleViews.add(ArticleViewType(it, ArticleViewType.VIEW_TYPE_PROJECT))
                        }
                        listAdapter.submitList(articleViews)
                        listAdapter.notifyDataSetChanged()
                    } else {
//                        listAdapter.loadMoreModule.loadMoreComplete()
//                        listAdapter.loadMoreModule.loadMoreEnd(false)
                    }
                }

            })
        }
    }

    private fun initEvent() {
        LiveEventBus.get(ARTICLE_LIST, MutableList::class.java).observe(this, Observer {
            if (it.isNotEmpty()) {
                articleList.addAll(it as MutableList<ArticleBean>)
                articleList.forEach {
                    articleViews.add(ArticleViewType(it, ArticleViewType.VIEW_TYPE_ARTICLE))
                }
                listAdapter.submitList(articleViews)
                listAdapter.notifyDataSetChanged()
            }
        })
        LiveEventBus.get(PROJECT_LIST, MutableList::class.java).observe(this, Observer {
            if (it.isNotEmpty()) {
                projectList.addAll(it as MutableList<ArticleBean>)
                projectList.forEach {
                    articleViews.add(ArticleViewType(it, ArticleViewType.VIEW_TYPE_PROJECT))
                }
                listAdapter.submitList(articleViews)
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
//            listAdapter.setOnItemClickListener { adapter, view, position ->
//                val viewType = articleViews[position]
//                if (viewType.itemType == ArticleViewType.VIEW_TYPE_ARTICLE) {
//                    val articleBean = viewType.item as ArticleBean
//                    startKtxActivity<ArticleActivity>(
//                        values = mutableListOf(
//                            ArticleActivity.TITLE to articleBean.title,
//                            ArticleActivity.URL to articleBean.link
//                        )
//                    )
//                    overridePendingTransition(R.anim.right_to_left_enter, R.anim.no_anim)
//                } else {
//                    val articleBean = viewType.item as ArticleBean
//                    startKtxActivity<ArticleActivity>(
//                        values = mutableListOf(
//                            ArticleActivity.TITLE to articleBean.title,
//                            ArticleActivity.URL to articleBean.link
//                        )
//                    )
//                    overridePendingTransition(R.anim.right_to_left_enter, R.anim.no_anim)
//                }
//            }
//            listAdapter.loadMoreModule.setOnLoadMoreListener {
//                page++
//                if (showType == ARTICLE_LIST) {
//                    getArticle()
//                } else {
//                    getProject()
//                }
//            }
        }
    }

    private fun getArticle() {
        mViewModel.getHotArticle(page)
    }

    private fun getProject() {
        mViewModel.getHotProject(page)
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