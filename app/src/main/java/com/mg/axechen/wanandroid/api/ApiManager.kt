package com.mg.axechen.wanandroid.api

import com.mg.axechen.libdata.RetrofitHelper

class ApiManager {
    var mApi: AppApi? = null

    // 这种方式是全局一个service对象。优点、1、整个app只有一个retrofit对象被创建 缺点：所有的接口写在一个service对象中
    fun getApiService(): AppApi {
        val okHttpClient = RetrofitHelper.buildOkHttpClient()

        if (mApi == null) {
            mApi =
                RetrofitHelper.buildRetrofit(getBaseUrl(), okHttpClient).create(AppApi::class.java)
        }
        return mApi!!
    }

    // 这种方式多个service，优点1、不同模块对应不同的service，便于管理  缺点：每个模块都会创建一个Retrofit对象
    fun <T> getService(service: Class<T>): T {
        val okHttpClient = RetrofitHelper.buildOkHttpClient()
        return RetrofitHelper.buildRetrofit(getBaseUrl(), okHttpClient).create(service)
    }

    private fun getBaseUrl(): String {
        return ApiConstant.API_PRODUCT
    }
}