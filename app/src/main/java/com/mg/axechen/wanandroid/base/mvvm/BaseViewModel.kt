package com.mg.axechen.wanandroid.base.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mg.axechen.wanandroid.api.response.ResponseEntity
import com.mg.axechen.wanandroid.api.response.ResponseList
import com.mg.axechen.wanandroid.api.response.ResultResponse
import kotlinx.coroutines.*

/**
 * ViewMoel基类
 * 提供携程，数据访问、解析、异常处理。
 */
open class BaseViewModel : ViewModel() {

    // 携程异步主要方法
    fun launch(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(exceptionHandler) { block() }
    }

    // 异常处理
    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->

    }

    suspend fun <T : Any> executeResponseEntity(response: ResponseEntity<T>): ResultResponse<T> {
        return withContext(Dispatchers.IO) {
            ResultResponse.SuccessEntity(response.data!!, "message")
        }
    }

    suspend fun <T : Any> executeResponseList(response: ResponseList<T>): ResultResponse<MutableList<T>> {
        return withContext(Dispatchers.IO) {
            ResultResponse.SuccessList(response.data!!, "message")
        }
    }

}