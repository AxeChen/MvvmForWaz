package com.mg.axechen.wanandroid.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel

abstract class BaseActivity : AppCompatActivity(), CoroutineScope by MainScope() {

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

    override fun onDestroy() {
        cancel()
        super.onDestroy()

    }
}