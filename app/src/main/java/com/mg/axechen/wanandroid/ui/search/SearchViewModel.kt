package com.mg.axechen.wanandroid.ui.search

import androidx.lifecycle.MutableLiveData
import com.mg.axechen.wanandroid.api.repository.SearchRepository
import com.mg.axechen.wanandroid.api.response.ResultResponse
import com.mg.axechen.wanandroid.base.mvvm.BaseViewModel
import com.mg.axechen.wanandroid.model.ArticleListBean
import com.mg.axechen.wanandroid.model.HotWord

class SearchViewModel : BaseViewModel() {

    private val searchRepository by lazy { SearchRepository() }

    val uiState = MutableLiveData<SearchDataViewModel>()

    data class SearchDataViewModel(
        val hotWord: MutableList<HotWord>?,
        var articleListBean: ArticleListBean?
    )

    private fun emitUiState(
        hotWord: MutableList<HotWord>? = null,
        articleListBean: ArticleListBean? = null
    ) {
        uiState.value = SearchDataViewModel(hotWord, articleListBean)
    }

    fun getHotWord() {
        launch {
            executeResponseList(searchRepository.getHotWords()).let {
                when (it) {
                    is ResultResponse.SuccessList -> {
                        emitUiState(hotWord = it.data)
                    }
                    is ResultResponse.Error -> {

                    }
                }
            }
        }
    }

    fun searchArticle(page: Int, queryText: String) {
        launch {
            executeResponseEntity(searchRepository.searchArticle(page, queryText)).let {
                when (it) {
                    is ResultResponse.SuccessEntity -> {
                        emitUiState(articleListBean = it.data)
                    }
                    is ResultResponse.Error -> {

                    }
                }
            }
        }
    }
}