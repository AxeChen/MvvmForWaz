package com.mg.axechen.wanandroid.ui.login

import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.mvvm.BaseVMFragment
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : BaseVMFragment<SignViewModel>() {

    override fun setLayoutId(): Int = R.layout.fragment_register

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
            }
    }

    override fun initView() {
        super.initView()
    }

    override fun initData() {
        super.initData()
        tvBackPress.setOnClickListener {
            activity?.onBackPressed()
        }
    }
}