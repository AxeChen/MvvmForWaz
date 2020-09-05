package com.mg.axechen.wanandroid.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar

abstract class BaseActivity : AppCompatActivity() {

    lateinit var mImmersionBar: ImmersionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStatusBar()
        setContentView(setLayoutId())
        initView()
        initData()
    }

    @LayoutRes
    protected abstract fun setLayoutId(): Int

    open fun initView() {}
    open fun initData() {}

    open fun initStatusBar() {
        mImmersionBar = ImmersionBar.with(this)
        mImmersionBar.fitsSystemWindows(false).transparentStatusBar().statusBarDarkFont(false, 0.2f)
            .init()
    }
}