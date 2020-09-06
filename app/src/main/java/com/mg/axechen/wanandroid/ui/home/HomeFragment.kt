package com.mg.axechen.wanandroid.ui.home

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.mvvm.BaseVMFragment
import com.mg.axechen.wanandroid.model.ArticleBean
import com.mg.axechen.wanandroid.model.BannerBean
import com.mg.axechen.wanandroid.model.ProjectBean
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseVMFragment<HomeFragmentViewModel>() {

    override fun setLayoutId(): Int = R.layout.fragment_home

    private var views: MutableList<HomeCardViewType> = mutableListOf()

    private val cardAdapter by lazy { HomeCardAdapter(requireActivity(), views) }

    override fun initView() {
        super.initView()
        initRecycler()
    }

    private fun initRecycler() {
        homeRecyclerView.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cardAdapter
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

    }

    private fun buildBannerData(bannerBeanList: MutableList<BannerBean>) {
        var showBanners: MutableList<BannerBean> = mutableListOf()
        showBanners.addAll(bannerBeanList)
        views.add(HomeCardViewType(showBanners, HomeCardViewType.VIEW_TYPE_BANNER))
        cardAdapter.setList(views)
        cardAdapter.notifyDataSetChanged()
    }

    private fun buildData(articles: MutableList<ArticleBean>) {
        views.add(HomeCardViewType("showArticle", HomeCardViewType.VIEW_CARD_REWARD))
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