package com.mg.axechen.wanandroid.api.response

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName

data class ResponseEntity<T>(
    @SerializedName("info")
    val errorCode: Int,
    @SerializedName("msg")
    val errorMsg: String,
    @JsonAdapter(EntitySecurityAdapter::class)
    val data: T?,
    val callback: String
)

data class ResponseList<T>(
    @SerializedName("info")
    val errorCode: Int,
    @SerializedName("msg")
    val errorMsg: String,
    @JsonAdapter(ListSecurityAdapter::class)
    val data: MutableList<T>?,
    val callback: String
)