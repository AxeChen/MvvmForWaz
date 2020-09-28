package com.mg.axechen.wanandroid.api.repository

import com.mg.axechen.wanandroid.api.ApiManager
import com.mg.axechen.wanandroid.api.response.ResponseList
import com.mg.axechen.wanandroid.model.ProjectKind
import com.mg.axechen.wanandroid.model.WeChatBean

class WeChatRepository {

    val api by lazy { ApiManager.getApiService() }

    suspend fun getWeChatGroups(): ResponseList<ProjectKind> {
        return api.getWetChat()
    }
}