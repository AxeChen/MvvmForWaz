package com.mg.axechen.wanandroid.ui.home

import android.os.Handler
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeremyliao.liveeventbus.LiveEventBus
import com.mg.axechen.libcommon.startKtxActivity
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.mvvm.BaseVMFragment
import com.mg.axechen.wanandroid.base.webview.WebViewActivity
import com.mg.axechen.wanandroid.model.ArticleBean
import com.mg.axechen.wanandroid.model.BannerBean
import com.mg.axechen.wanandroid.ui.article.ArticleListActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseVMFragment<ArticleViewModel>() {

    override fun setLayoutId(): Int = R.layout.fragment_home

    private var views: MutableList<HomeCardViewType> = mutableListOf()

    private val cardAdapter by lazy { HomeCardAdapter(requireActivity(), views) }

    private var articles: MutableList<ArticleBean> = mutableListOf()

    override fun initView() {
        super.initView()
        initRecycler()
    }

    private fun initRecycler() {
        homeRecyclerView.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cardAdapter
            cardAdapter.apply {
                homeItemClickListener = object : HomeCardAdapter.HomeItemClickListener {
                    override fun articleClickListener(articleBean: ArticleBean) {
                        startKtxActivity<WebViewActivity>(
                            values = mutableListOf(
                                WebViewActivity.TITLE to articleBean.title,
                                WebViewActivity.URL to articleBean.link
                            )
                        )
                    }
                }

                setOnItemChildClickListener { adapter, view, position ->
                    when (view.id) {
                        R.id.rtToMore -> {
                            startKtxActivity<WebViewActivity>(
                                values = mutableListOf(
                                    WebViewActivity.TITLE to "关于玩Android",
                                    WebViewActivity.URL to "https://www.wanandroid.com/index"
                                )
                            )
                        }
                        R.id.rtToAuthor -> {
                            startKtxActivity<WebViewActivity>(
                                values = mutableListOf(
                                    WebViewActivity.TITLE to "关于作者",
                                    WebViewActivity.URL to "https://www.jianshu.com/u/05f7d21f41ed"
                                )
                            )
                        }
                        R.id.ivCardClose -> {
                            var viewType = views[position]
                            views.removeAt(position)
                            notifyItemRemoved(position)
                        }

                        R.id.tvMoreArticle -> {
                            startKtxActivity<ArticleListActivity>(
                                values = mutableListOf(
                                    ArticleListActivity.SHOW_TYPE to ArticleListActivity.ARTICLE_LIST
                                )
                            )
                            LiveEventBus.get(ArticleListActivity.ARTICLE_LIST).post(articles)
                        }
                    }
                }
            }
        }
    }

    override fun initData() {
        super.initData()
        observerData()
        getHotArticle()
    }


    private fun observerData() {
        mViewModel.run {
            uiState.observe(this@HomeFragment, Observer {
                it.articleList?.run {
                    if (isNotEmpty()) {
                        articles.addAll(this)
                        // 构建数据
                        buildData(this)
                    } else {
                        // 继续请求数据
                    }
                    getBanner()

                }

                it.bannerBeanList?.run {
                    if (isNotEmpty()) {
                        buildBannerData(this)
                    } else {

                    }
                    getProject()
                }

                it.projectList?.run {
                    if (isNotEmpty()) {
                        buildProjectData(this)
                    } else {

                    }
                }
            })
        }
    }

    private fun buildProjectData(projects: MutableList<ArticleBean>) {
        var showProject: MutableList<ArticleBean> = mutableListOf()
        for (it in 0 until 6) {
            showProject.add(projects[it])
        }
        views.add(HomeCardViewType(showProject, HomeCardViewType.VIEW_TYPE_HOT_PROJECT))
        cardAdapter.setList(views)
        cardAdapter.notifyDataSetChanged()

        Handler().postDelayed({
            views.add(0, HomeCardViewType("showArticle", HomeCardViewType.VIEW_CARD_ABOUT_APP))
            cardAdapter.notifyItemChanged(0)
        }, 1000)

        Handler().postDelayed({
            views.add(1, HomeCardViewType("showArticle", HomeCardViewType.VIEW_CARD_DEVELOPER))
            cardAdapter.notifyItemInserted(1)
        }, 2000)

        Handler().postDelayed({
            views.add(1, HomeCardViewType("showArticle", HomeCardViewType.VIEW_CARD_SIGN))
            cardAdapter.notifyItemInserted(1)
        }, 3000)

    }

    private fun buildBannerData(bannerBeanList: MutableList<BannerBean>) {
        var showBanners: MutableList<BannerBean> = mutableListOf()
        showBanners.addAll(bannerBeanList)
        views.add(HomeCardViewType(showBanners, HomeCardViewType.VIEW_TYPE_BANNER))
        cardAdapter.setList(views)
        cardAdapter.notifyDataSetChanged()
    }

    private fun buildData(articles: MutableList<ArticleBean>) {
        var showArticle: MutableList<ArticleBean> = mutableListOf()
        for (it in 0 until 5) {
            showArticle.add(articles[it])
        }
        views.add(HomeCardViewType(showArticle, HomeCardViewType.VIEW_TYPE_HOT_ARTICLE))
        cardAdapter.setList(views)
        cardAdapter.notifyDataSetChanged()
    }


    private fun getHotArticle() {
        mViewModel.getHotArticle(1)
    }

    private fun getBanner() {
        mViewModel.getBannerList()
    }

    private fun getProject() {
        mViewModel.getHotProject()
    }


}