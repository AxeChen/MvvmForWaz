package com.mg.axechen.wanandroid.app

import android.app.Application
import com.kingja.loadsir.core.LoadSir
import com.tencent.mmkv.MMKV

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)

        LoadSir.beginBuilder()
            .commit()
    }
}