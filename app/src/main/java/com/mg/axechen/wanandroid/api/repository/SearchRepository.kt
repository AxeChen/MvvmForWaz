package com.mg.axechen.wanandroid.api.repository

import com.mg.axechen.wanandroid.api.ApiManager
import com.mg.axechen.wanandroid.api.response.ResponseEntity
import com.mg.axechen.wanandroid.api.response.ResponseList
import com.mg.axechen.wanandroid.model.ArticleListBean
import com.mg.axechen.wanandroid.model.HotWord

class SearchRepository {
    val api by lazy { ApiManager.getApiService() }

    suspend fun getHotWords(): ResponseList<HotWord> {
        return api.hotWords()
    }

    suspend fun searchArticle(page: Int, k: String): ResponseEntity<ArticleListBean> {
        return api.searchArticle(page, k)
    }
}