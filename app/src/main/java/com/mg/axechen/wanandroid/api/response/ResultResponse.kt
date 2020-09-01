package com.mg.axechen.wanandroid.api.response

sealed class ResultResponse<out T : Any> {

    data class SuccessEntity<T : Any>(val data: T, val message: String = "") : ResultResponse<T>()

    data class SuccessList<T : Any>(val data: MutableList<T>, val message: String = "") :
        ResultResponse<MutableList<T>>()

    data class Error(val code: Int = 0,val error: String) : ResultResponse<Nothing>()

}