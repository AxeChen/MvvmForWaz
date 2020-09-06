package com.mg.axechen.wanandroid.api.repository

import com.mg.axechen.wanandroid.api.ApiManager
import com.mg.axechen.wanandroid.api.response.ResponseEntity
import com.mg.axechen.wanandroid.api.response.ResponseList
import com.mg.axechen.wanandroid.model.ArticleBean
import com.mg.axechen.wanandroid.model.ArticleListBean
import com.mg.axechen.wanandroid.model.ProjectBean

class ProjectRepository {
    val api by lazy { ApiManager.getApiService() }


    suspend fun getHotProject(page: Int): ResponseEntity<ArticleListBean> {
        return api.getHotProject(page)
    }
}