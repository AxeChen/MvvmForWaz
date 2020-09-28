package com.mg.axechen.wanandroid.ui.follow

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_project.*

class FollowFragment : BaseFragment() {

    override fun setLayoutId(): Int = R.layout.fragment_project

    override fun initView() {
        super.initView()
        initViewPager()
    }

    override fun initData() {
        super.initData()
    }

    private fun initViewPager() {
        followViewPager.run {
            offscreenPageLimit = 2
            adapter = object : FragmentStateAdapter(this@FollowFragment) {
                override fun getItemCount(): Int = 2

                override fun createFragment(position: Int): Fragment = showFragment(position)
            }
        }

        TabLayoutMediator(followTabLayout, followViewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "体系"
                }
                1 -> {
                    tab.text = "公众号"
                }
            }

        }.attach()
    }

    private fun showFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                FollowProjectFragment()
            }
            1 -> {
                FollowWeChatFragment()
            }
            else -> {
                FollowProjectFragment()
            }
        }
    }


}