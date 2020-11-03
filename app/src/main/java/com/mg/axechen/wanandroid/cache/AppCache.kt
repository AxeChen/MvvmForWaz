package com.mg.axechen.wanandroid.cache

import com.mg.axechen.libdata.string
import com.tencent.mmkv.MMKV

object AppCache {
    private val defaultMMKV by lazy { MMKV.defaultMMKV() }

    const val FOLLOW_PROJECT = "follow_project"

    const val FOLLOW_WECHAT = "follow_wechat"

    const val SEARCH_HISTORY_CACHE = "search_history_cache"

    var searchHistoryCache by defaultMMKV.string(SEARCH_HISTORY_CACHE, "")

}