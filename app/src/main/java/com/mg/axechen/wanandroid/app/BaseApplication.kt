package com.mg.axechen.wanandroid.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.kingja.loadsir.core.LoadSir
import com.tencent.mmkv.MMKV

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)

        LoadSir.beginBuilder()
            .commit()
    }

    @Suppress("INACCESSIBLE_TYPE")
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}