package com.mg.axechen.wanandroid.base.mvvm

import androidx.lifecycle.ViewModelProvider
import com.mg.axechen.libcommon.ClassHelper
import com.mg.axechen.wanandroid.base.BaseActivity

abstract class BaseVMActivity<VM : BaseViewModel> : BaseActivity() {

    lateinit var mViewModel: VM

    override fun initView() {
        super.initView()
        initVM()
    }

    private fun initVM() {
        ClassHelper.getClass<VM>(this).let {
            mViewModel = ViewModelProvider(this).get(it)
        }
    }
}