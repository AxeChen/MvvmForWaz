package com.mg.axechen.wanandroid.test

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.mg.axechen.wanandroid.api.ApiManager
import kotlinx.coroutines.*

/**
 * work测试代码
 */
class TestWork : Worker {

    val api by lazy { ApiManager.getApiService() }

    var gson = Gson()

    constructor(context: Context, workerParams: WorkerParameters) : super(context, workerParams)

    override fun doWork(): Result {
        var resultData: Data? = null
        runBlocking {
            Log.i("TestWork", "start")
            var responseList = api.getProjectKind()
            resultData = Data.Builder().putString("result", gson.toJson(responseList.data)).build()
            Log.i("TestWork", "end")
        }
        Log.i("TestWork", "getdata")
        return Result.success(resultData!!)
    }

}