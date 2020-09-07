package com.mg.axechen.wanandroid.ui.guide

import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.mvvm.BaseVMActivity
import kotlinx.android.synthetic.main.activity_onboard.*

class OnBoardingActivity : BaseVMActivity<OnBoardViewModel>() {

    override fun setLayoutId(): Int = R.layout.activity_onboard

    private fun initToolbar() {
        toolBar?.run {
            title = "猜你喜欢"
            setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
            setNavigationOnClickListener { finish() }
        }
    }

    override fun initView() {
        super.initView()
        mImmersionBar.fitsSystemWindows(true).statusBarDarkFont(true, 0.2f)
            .init()
        initToolbar()
    }

    override fun initData() {
        super.initData()
    }


}