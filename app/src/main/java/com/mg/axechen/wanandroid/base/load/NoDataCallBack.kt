package com.mg.axechen.wanandroid.base.load

import com.kingja.loadsir.callback.Callback
import com.mg.axechen.wanandroid.R

class NoDataCallBack : Callback() {

    override fun onCreateView(): Int = R.layout.layout_load_no_data
}