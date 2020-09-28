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

    @GET("article/listproject/{page}/json")
    suspend fun getHotProject(@Path("page") page: Int): ResponseEntity<ArticleListBean>

    @GET("project/tree/json")
    suspend fun getProjectKind(): ResponseList<ProjectKind>

    @GET("wxarticle/chapters/json")
    suspend fun getWetChat(): ResponseList<ProjectKind>

    @GET("article/list/{page}/json?cid={cid}")
    suspend fun getArticleListByCid(
        @Path("page") page: Int,
        @Path("cid") cid: Int
    ): ResponseList<ArticleListBean>

}