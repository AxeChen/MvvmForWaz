package com.mg.axechen.wanandroid.ui.follow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mg.axechen.wanandroid.api.repository.ArticleRepository
import com.mg.axechen.wanandroid.api.response.ResultResponse
import com.mg.axechen.wanandroid.base.mvvm.BaseViewModel
import com.mg.axechen.wanandroid.model.ArticleListBean

class FollowViewModel : BaseViewModel() {

    private val articleRepository by lazy { ArticleRepository() }

    private val _uiState = MutableLiveData<FollowDataViewModel>()
    val uiState: LiveData<FollowDataViewModel>
        get() = _uiState

    data class FollowDataViewModel(
        val followDataList: MutableList<ArticleListBean>?
    )

    fun emitUiState(followDataList: MutableList<ArticleListBean>? = null) {
        _uiState.value = FollowDataViewModel(followDataList)
    }

    fun getArticleByCid(page: Int, cid: Int) {
        launch {
            executeResponseList(articleRepository.getArticleByCid(page, cid)).let {
                when (it) {
                    is ResultResponse.SuccessList -> {
                        emitUiState(followDataList = it.data)
                    }
                    is ResultResponse.Error -> {

                    }
                }
            }
        }
    }

}