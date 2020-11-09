package com.mg.axechen.wanandroid.ui.login

import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.mvvm.BaseVMActivity
import kotlinx.android.synthetic.main.layout_toolbar.*

class RegisterActivity : BaseVMActivity<SignViewModel>() {

    override fun setLayoutId(): Int = R.layout.activity_register

    override fun initView() {
        super.initView()
        mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.colorPrimary)
            .statusBarDarkFont(false, 0.2f)
            .init()
        toolbar?.run {
            toolbarTitle.text = "用户注册"
            setNavigationOnClickListener { finish() }
        }
    }

    override fun initData() {
        super.initData()
    }
}