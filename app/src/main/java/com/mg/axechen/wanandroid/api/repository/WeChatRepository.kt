package com.mg.axechen.wanandroid.api.repository

import com.mg.axechen.wanandroid.api.ApiManager

class WeChatRepository {
    val api by lazy { ApiManager.getApiService() }

    suspend fun getWeChatGroups(){

    }
}