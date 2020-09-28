package com.mg.axechen.wanandroid.api.repository

import com.mg.axechen.wanandroid.api.ApiManager
import com.mg.axechen.wanandroid.api.response.ResponseEntity
import com.mg.axechen.wanandroid.api.response.ResponseList
import com.mg.axechen.wanandroid.model.ArticleListBean
import com.mg.axechen.wanandroid.model.BannerBean

class ArticleRepository {

    val api by lazy { ApiManager.getApiService() }

    suspend fun getArticleList(page: Int): ResponseEntity<ArticleListBean> {
        return api.getArticleList(page)
    }

    suspend fun getBannerList(): ResponseList<BannerBean> {
        return api.getBannerList()
    }

    suspend fun getArticleByCid(page: Int, cid: Int): ResponseList<ArticleListBean> {
        return api.getArticleListByCid(page, cid)
    }


}