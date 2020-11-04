package com.mg.axechen.wanandroid.base.load

import android.content.Context
import android.view.View
import com.kingja.loadsir.callback.Callback
import com.mg.axechen.wanandroid.R

class LoadingCallback : Callback() {

    override fun onCreateView(): Int = R.layout.layout_load_loading

    override fun getSuccessVisible(): Boolean {
        return super.getSuccessVisible()
    }

    override fun onReloadEvent(context: Context?, view: View?): Boolean {
        return true
    }
}