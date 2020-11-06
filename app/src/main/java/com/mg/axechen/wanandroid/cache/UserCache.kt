package com.mg.axechen.wanandroid.cache

import com.mg.axechen.libdata.string
import com.tencent.mmkv.MMKV

/**
 * 用户Cache
 */
object UserCache {

    private const val USER_NAME = "user_name"

    private const val USER_AVATAR = "user_avatar"

    private val defaultMMKV by lazy { MMKV.defaultMMKV() }

    var userName by defaultMMKV.string(USER_NAME, "")

    var userAvatar by defaultMMKV.string(USER_AVATAR, "")

}