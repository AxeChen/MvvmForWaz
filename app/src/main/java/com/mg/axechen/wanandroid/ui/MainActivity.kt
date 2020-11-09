package com.mg.axechen.wanandroid.ui

import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mg.axechen.libcommon.startKtxActivity
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.BaseActivity
import com.mg.axechen.wanandroid.ui.collect.CollectFragment
import com.mg.axechen.wanandroid.ui.follow.FollowFragment
import com.mg.axechen.wanandroid.ui.home.HomeFragment
import com.mg.axechen.wanandroid.ui.mine.MineFragment
import com.mg.axechen.wanandroid.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun setLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        super.initView()
        initToolBar()
        initViewPager()
    }

    private fun initToolBar() {
        toolBar?.run {
            setTitleTextColor(ContextCompat.getColor(this@MainActivity, R.color.white))
            setSupportActionBar(this)
        }
    }

    private fun initViewPager() {
        mainViewPager?.run {
            offscreenPageLimit = 4
            adapter = object : FragmentStateAdapter(this@MainActivity) {
                override fun getItemCount(): Int = 4
                override fun createFragment(position: Int): Fragment = showFragment(position)
            }

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    setSelectIcon(position)
                }
            })
        }

        mainTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                var textView = tab?.customView?.findViewById<TextView>(R.id.tvTabText)
                textView?.setTextColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.line
                    )
                )
                textView?.textSize = 16f
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                var textView = tab?.customView?.findViewById<TextView>(R.id.tvTabText)
                textView?.setTextColor(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.white
                    )
                )
                textView?.textSize = 20f

            }

        })
        TabLayoutMediator(mainTabLayout, mainViewPager) { tab, position ->
            var tabView = layoutInflater.inflate(R.layout.home_tab_text, null)
            var tabText = tabView.findViewById<TextView>(R.id.tvTabText)
            tabText.text = setTabTitle(position)
            tab.customView = tabView
        }.attach()

    }

    private fun setTabTitle(position: Int): String {
        return when (position) {
            0 -> "热点"
            1 -> "关注"
            2 -> "收藏"
            3 -> "我的"
            else -> ""
        }
    }

    private fun setSelectIcon(selectPosition: Int) {

        for (it in 0 until mainTabLayout.tabCount) {
            if (it == selectPosition) {
                mainTabLayout.getTabAt(it)?.icon?.setTint(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
            } else {
                mainTabLayout.getTabAt(it)?.icon?.setTint(
                    ContextCompat.getColor(
                        this,
                        R.color.green_38875f
                    )
                )
            }
        }
    }

    private fun showFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment()
            }
            1 -> {
                FollowFragment()
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

    override fun initData() {
        super.initData()
        mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.colorPrimary)
            .statusBarDarkFont(false, 0.2f)
            .init()
    }
    

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.actionSearch -> {
                startKtxActivity<SearchActivity>()
            }
        }

        return super.onOptionsItemSelected(item)
    }

}