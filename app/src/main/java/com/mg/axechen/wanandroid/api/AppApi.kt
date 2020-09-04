package com.mg.axechen.wanandroid.api

import com.mg.axechen.wanandroid.api.response.ResponseEntity
import com.mg.axechen.wanandroid.api.response.ResponseList
import com.mg.axechen.wanandroid.model.ArticleListBean
import com.mg.axechen.wanandroid.model.BannerBean
import retrofit2.http.GET
import retrofit2.http.Path

interface AppApi {

    @GET("article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): ResponseEntity<ArticleListBean>

    @GET("banner/json")
    suspend fun getBannerList(): ResponseList<BannerBean>

}