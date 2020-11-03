package com.mg.axechen.wanandroid.ui

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.google.android.material.tabs.TabLayoutMediator
import com.mg.axechen.libcommon.startKtxActivity
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.BaseActivity
import com.mg.axechen.wanandroid.ui.collect.CollectFragment
import com.mg.axechen.wanandroid.ui.follow.FollowFragment
import com.mg.axechen.wanandroid.ui.home.HomeFragment
import com.mg.axechen.wanandroid.ui.mine.MineFragment
import com.mg.axechen.wanandroid.test.TestWork
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
            title = "热门"
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

        TabLayoutMediator(mainTabLayout, mainViewPager) { tab, position ->
            tab.setIcon(setTabIcon(position))
        }.attach()

    }

    private fun setTabIcon(position: Int): Int {
        return when (position) {
            0 -> R.drawable.ic_main_tab_article
            1 -> R.drawable.ic_main_tab_project
            2 -> R.drawable.ic_main_tab_collect
            3 -> R.drawable.ic_main_tab_mine
            else -> R.drawable.ic_main_tab_article
        }
    }

    private fun setSelectIcon(selectPosition: Int) {
        when (selectPosition) {
            0 -> toolBar.title = "热点"
            1 -> toolBar.title = "关注"
            2 -> toolBar.title = "收藏"
            3 -> toolBar.title = "我的"
        }

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


//    private fun testWork() {
//        var request = makeOneTimeWorkRequest()
//        WorkManager.getInstance().enqueue(request)
//        WorkManager.getInstance().getWorkInfoByIdLiveData(request.id).observe(this, Observer {
//            when (it.state) {
//                WorkInfo.State.SUCCEEDED -> {
//                    var getData = it.outputData
//                    Log.i("TestWork", getData.getString("result"))
//                }
//            }
//        })
//    }
//
//    private fun makeOneTimeWorkRequest(): OneTimeWorkRequest {
//        val inputData: Data = Data.Builder()
//            .putInt("page", 1)
//            .build()
//        return OneTimeWorkRequest.Builder(TestWork::class.java).setInputData(inputData).build()
//    }

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