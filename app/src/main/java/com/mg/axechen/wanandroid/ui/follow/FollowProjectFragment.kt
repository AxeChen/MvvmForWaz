package com.mg.axechen.wanandroid.ui.follow

import androidx.lifecycle.Observer
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.BaseFragment
import com.mg.axechen.wanandroid.base.mvvm.BaseVMFragment

class FollowProjectFragment : BaseVMFragment<FollowViewModel>() {
    override fun setLayoutId(): Int = R.layout.fragment_follow_list

    override fun initView() {
        super.initView()
    }

    override fun initData() {
        super.initData()
        getProjectData()
        startObserver()
    }

    private fun startObserver() {
        mViewModel.run {
            uiState.observe(this@FollowProjectFragment, Observer {
                it.followDataList?.run {
                    if (isNotEmpty()) {

                    }
                }
            })
        }
    }

    private fun getProjectData() {
        mViewModel.getArticleByCid(1, 60)
    }
}