package com.mg.axechen.wanandroid.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mg.axechen.wanandroid.api.repository.ArticleRepository
import com.mg.axechen.wanandroid.api.response.ResultResponse
import com.mg.axechen.wanandroid.base.mvvm.BaseViewModel
import com.mg.axechen.wanandroid.model.ArticleBean
import com.mg.axechen.wanandroid.model.BannerBean

class HomeFragmentViewModel : BaseViewModel() {

    private val articleRepository by lazy { ArticleRepository() }

    private val _uiState = MutableLiveData<HomeDataViewModel>()
    val uiState: LiveData<HomeDataViewModel>
        get() = _uiState

    data class HomeDataViewModel(
        val articleList: MutableList<ArticleBean>?,
        val bannerBeanList: MutableList<BannerBean>?
    )

    private fun emitUiState(
        articleList: MutableList<ArticleBean>? = null,
        bannerBeanList: MutableList<BannerBean>? = null
    ) {
        _uiState.value = HomeDataViewModel(articleList, bannerBeanList)
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

    }

    fun getProjectByType() {

    }

}