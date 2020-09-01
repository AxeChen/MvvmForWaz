package com.mg.axechen.wanandroid.api

import com.mg.axechen.wanandroid.api.response.ResponseEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface AppApi {

    @GET("article/list/{page}/json")
    suspend fun userLogin(@Path("page") page: Int): ResponseEntity<Any>

}