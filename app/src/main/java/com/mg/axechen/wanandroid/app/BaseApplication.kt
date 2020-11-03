package com.mg.axechen.wanandroid.app

import android.app.Application
import com.tencent.mmkv.MMKV

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
    }
}