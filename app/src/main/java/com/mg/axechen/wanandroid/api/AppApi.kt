package com.mg.axechen.wanandroid.api

import com.mg.axechen.wanandroid.api.response.ResponseEntity
import com.mg.axechen.wanandroid.api.response.ResponseList
import com.mg.axechen.wanandroid.model.*
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Path

interface AppApi {

    @GET("article/list/{page}/json")
    suspend fun getArticleList(@Path("page") page: Int): ResponseEntity<ArticleListBean>

    @GET("banner/json")
    suspend fun getBannerList(): ResponseList<BannerBean>

    @HTTP(path = "https://wanandroid.com/article/listproject/{page}/json", method = "GET")
    suspend fun getHotProject(@Path("page") page: Int): ResponseEntity<ArticleListBean>

    @GET("project/tree/json")
    suspend fun getProjectKind():ResponseList<ProjectKind>

}