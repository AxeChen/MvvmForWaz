package com.mg.axechen.wanandroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mg.axechen.wanandroid.api.repository.ArticleRepository
import com.mg.axechen.wanandroid.api.repository.ProjectRepository
import com.mg.axechen.wanandroid.api.response.ResultResponse
import com.mg.axechen.wanandroid.base.mvvm.BaseViewModel
import com.mg.axechen.wanandroid.model.ArticleBean
import com.mg.axechen.wanandroid.model.BannerBean
import com.mg.axechen.wanandroid.model.ProjectBean

class ArticleViewModel : BaseViewModel() {

    private val articleRepository by lazy { ArticleRepository() }

    private val projectRepository by lazy { ProjectRepository() }

    private val _uiState = MutableLiveData<HomeDataViewModel>()
    val uiState: LiveData<HomeDataViewModel>
        get() = _uiState

    data class HomeDataViewModel(
        val articleList: MutableList<ArticleBean>?,
        val bannerBeanList: MutableList<BannerBean>?,
        val projectList: MutableList<ArticleBean>?
    )

    private fun emitUiState(
        articleList: MutableList<ArticleBean>? = null,
        bannerBeanList: MutableList<BannerBean>? = null,
        projectList: MutableList<ArticleBean>? = null
    ) {
        _uiState.value = HomeDataViewModel(articleList, bannerBeanList, projectList)
    }

    fun getHotArticle(page: Int) {
        launch {
            executeResponseEntity(articleRepository.getArticleList(page)).let {
                when (it) {
                    is ResultResponse.SuccessEntity -> {
                        emitUiState(articleList = it.data.datas)
                    }

                    is ResultResponse.Error -> {
                        emitUiState(articleList = mutableListOf())
                    }
                }
            }
        }
    }

    fun getBannerList() {
        launch {
            executeResponseList(articleRepository.getBannerList()).let {
                when (it) {
                    is ResultResponse.SuccessList -> {
                        emitUiState(bannerBeanList = it.data)
                    }
                    is ResultResponse.Error -> {
                        emitUiState(bannerBeanList = mutableListOf())
                    }
                }
            }
        }
    }

    fun getHotProject() {
        launch {
            executeResponseEntity(projectRepository.getHotProject(0)).let {
                when (it) {
                    is ResultResponse.SuccessEntity -> {
                        emitUiState(projectList = it.data.datas)
                    }
                    is ResultResponse.Error -> {
                        emitUiState(projectList = mutableListOf())
                    }
                }
            }
        }
    }

    fun getProjectByType() {

    }

}