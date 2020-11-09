package com.mg.axechen.wanandroid.api.repository

import com.mg.axechen.wanandroid.api.ApiManager
import com.mg.axechen.wanandroid.api.response.ResponseEntity

class UserRepository {
    val api by lazy { ApiManager.getApiService() }

    suspend fun userLogin(userName: String, userPwd: String): ResponseEntity<Any> {
        return api.userLogin(userName, userPwd)
    }

    suspend fun userRegister(): ResponseEntity<Any> {
        return api.userRegister()
    }
}