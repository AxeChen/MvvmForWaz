package com.mg.axechen.wanandroid.ui.login

import android.util.Log
import android.view.MenuItem
import androidx.navigation.Navigation
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.mvvm.BaseVMActivity
import kotlinx.android.synthetic.main.layout_toolbar.*

class LoginActivity : BaseVMActivity<SignViewModel>() {

    override fun setLayoutId(): Int = R.layout.activity_login2

    override fun initView() {
        super.initView()
        mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.colorPrimary)
            .statusBarDarkFont(false, 0.2f)
            .init()
        toolbar?.run {
            elevation = 0f
            setSupportActionBar(this)
        }
        addNavigationListener()
    }

    private fun addNavigationListener() {
        val controller = Navigation.findNavController(this, R.id.sign_fragment)
        controller.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.loginFragment -> {
                    supportActionBar?.run {
                        title = "用户登录"
                    }
                }
                R.id.registerFragment -> {
                    supportActionBar?.run {
                        title = "用户注册"
                    }
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        var fragment = supportFragmentManager.fragments.first()
        if (fragment is LoginFragment) {
            return super.onSupportNavigateUp()
        } else {
            val controller = Navigation.findNavController(this, R.id.sign_fragment)
            return controller.navigateUp()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                var fragment = supportFragmentManager.fragments.first()
                if (fragment is LoginFragment) {
                    finish()
                } else {
                    onBackPressed()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initData() {
        super.initData()
    }

}