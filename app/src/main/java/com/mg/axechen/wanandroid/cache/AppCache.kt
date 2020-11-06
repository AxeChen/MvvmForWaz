package com.mg.axechen.wanandroid.cache

import com.mg.axechen.libdata.boolean
import com.mg.axechen.libdata.string
import com.tencent.mmkv.MMKV

/**
 * App公共Cache
 */
object AppCache {
    private val defaultMMKV by lazy { MMKV.defaultMMKV() }

    const val FOLLOW_PROJECT = "follow_project"

    const val FOLLOW_WECHAT = "follow_wechat"

    private const val SEARCH_HISTORY_CACHE = "search_history_cache"

    private const val SHOW_HOME_APP_INFO_CARD = "show_home_app_info_card"

    private const val SHOW_HOME_DEVELOP_CARD = "show_home_develop_card"

    private const val SHOW_HOME_SIGN_CARD = "show_home_sign_card"

    var searchHistoryCache by defaultMMKV.string(SEARCH_HISTORY_CACHE, "")

    // 是否展示首页APP信息卡片
    var showHomeAppCard by defaultMMKV.boolean(SHOW_HOME_APP_INFO_CARD, true)

    // 是否展示首页的开发信息卡片
    var showHomeDeveloperCard by defaultMMKV.boolean(SHOW_HOME_DEVELOP_CARD, true)

    // 是否展示首页的登陆信息卡片
    var showHomeSignCard by defaultMMKV.boolean(SHOW_HOME_SIGN_CARD, true)


}