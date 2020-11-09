package com.mg.axechen.wanandroid.cache

import com.mg.axechen.libdata.boolean
import com.mg.axechen.libdata.string
import com.tencent.mmkv.MMKV

/**
 * 用户Cache
 */
object UserCache {

    private const val USER_NAME = "user_name"

    private const val USER_AVATAR = "user_avatar"

    private const val IS_LOGIN = "is_login"

    private val defaultMMKV by lazy { MMKV.defaultMMKV() }

    var isLogin by defaultMMKV.boolean(IS_LOGIN, false)

    var userName by defaultMMKV.string(USER_NAME, "")

    var userAvatar by defaultMMKV.string(USER_AVATAR, "")

}