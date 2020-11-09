package com.mg.axechen.wanandroid.ui.article

import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.mvvm.BaseVMActivity
import com.mg.axechen.wanandroid.model.ArticleBean
import com.mg.axechen.wanandroid.ui.home.ArticleViewModel

class ArticleListActivity : BaseVMActivity<ArticleViewModel>() {

    // 文章
    private var articleList: MutableList<ArticleBean> = mutableListOf()

    // 项目
    private var projectList: MutableList<ArticleBean> = mutableListOf()

    companion object {
        const val ARTICLE_LIST_EVENT = "article_list_event"

        // 文章list
        const val ARTICLE_LIST = "article_list"

        // 项目list
        const val PROJECT_LIST = "project_list"
    }

    override fun setLayoutId(): Int = R.layout.activity_article_list

    override fun initData() {
        super.initData()
        initEvent()
    }

    private fun initEvent() {
        LiveEventBus.get(ARTICLE_LIST, MutableList::class.java).observe(this, Observer {
            if (it.isNotEmpty()) {
                articleList.addAll(it as MutableList<ArticleBean>)
            }
        })
    }

    override fun initView() {
        super.initView()


    }


}