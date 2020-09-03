package com.mg.axechen.wanandroid.ui

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.BaseActivity
import com.mg.axechen.wanandroid.ui.collect.CollectFragment
import com.mg.axechen.wanandroid.ui.home.HomeFragment
import com.mg.axechen.wanandroid.ui.mine.MineFragment
import com.mg.axechen.wanandroid.ui.project.ProjectFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun setLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        super.initView()
        initViewPager()
        initToolBar()
    }

    private fun initToolBar() {
        toolBar?.run {
            setTitle("热门")
        }
    }

    private fun initViewPager() {
        mainViewPager?.run {
            offscreenPageLimit = 4
            adapter = object : FragmentStateAdapter(this@MainActivity) {
                override fun getItemCount(): Int = 4

                override fun createFragment(position: Int): Fragment = showFragment(position)
            }
        }

        TabLayoutMediator(mainTabLayout, mainViewPager) { tab, position ->
            tab.text = "页面"
        }.attach()
    }

    private fun showFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment()
            }
            1 -> {
                ProjectFragment()
            }
            2 -> {
                CollectFragment()
            }
            3 -> {
                MineFragment()
            }
            else -> {
                HomeFragment()
            }


        }
    }


}