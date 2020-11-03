package com.mg.axechen.wanandroid.api

import com.mg.axechen.libdata.RetrofitHelper

object ApiManager {
    var mApi: AppApi? = null

    fun getApiService(): AppApi {
        val okHttpClient = RetrofitHelper.buildOkHttpClient()
        if (mApi == null) {
            synchronized(AppApi::class.java) {
                if (mApi == null) {
                    mApi =
                        RetrofitHelper.buildRetrofit(getBaseUrl(), okHttpClient)
                            .create(AppApi::class.java)
                }
            }
        }
        return mApi!!
    }

    fun <T> getService(service: Class<T>): T {
        val okHttpClient = RetrofitHelper.buildOkHttpClient()
        return RetrofitHelper.buildRetrofit(getBaseUrl(), okHttpClient).create(service)
    }

    private fun getBaseUrl(): String {
        return ApiConstant.API_PRODUCT
    }
}