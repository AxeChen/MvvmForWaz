package com.mg.axechen.wanandroid.ui.login

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.mg.axechen.libcommon.startKtxActivity
import com.mg.axechen.libcommon.toast
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.mvvm.BaseVMFragment
import com.mg.axechen.wanandroid.ui.MainActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseVMFragment<SignViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setLayoutId(): Int = R.layout.fragment_login

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
            }
    }

    override fun initView() {
        super.initView()
        tvToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        tvLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val userName = edLoginUserName.text.toString()
        if (userName.isEmpty()) {
            toast("请输入用户名！")
            return
        }
        val userPwd = edLoginPwd.text.toString()
        if (userPwd.isEmpty()) {
            toast("请输入密码！")
            return
        }
        mViewModel.userLogin(userName, userPwd)
    }

    override fun initData() {
        super.initData()
        startObserver()
    }

    private fun startObserver() {
        mViewModel.run {
            uiState.observe(this@LoginFragment, Observer {
                it.loginSuccess?.run {
                    if (true) {
                        toast("登录成功！")
                        startKtxActivity<MainActivity>()
                        activity?.finish()
                    } else {
                        toast("登录失败！")
                    }
                }
            })
        }
    }


}