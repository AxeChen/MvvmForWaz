package com.mg.axechen.wanandroid.ui.guide

import androidx.lifecycle.Observer
import com.google.android.flexbox.FlexboxLayoutManager
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.mvvm.BaseVMFragment
import com.mg.axechen.wanandroid.model.ProjectKind
import kotlinx.android.synthetic.main.fragment_onboard_nav.*

class WeChatNaviFragment : BaseVMFragment<WetChatNaviViewModel>() {

    override fun setLayoutId(): Int = R.layout.fragment_onboard_nav

    private var wetChats: MutableList<ProjectKind> = mutableListOf()


    private val listAdapter by lazy { NaviAdapter(wetChats) }

    override fun initView() {
        super.initView()
        rvList.run {
            layoutManager = FlexboxLayoutManager(activity)
            adapter = listAdapter
            listAdapter.setOnItemClickListener { adapter, view, position ->
                var projectKind = wetChats[position]
                if (listAdapter.selectIndexs.contains(projectKind.id)) {
                    listAdapter.selectIndexs.remove(projectKind.id)
                } else {
                    listAdapter.selectIndexs.add(projectKind.id)
                }
                listAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun initData() {
        super.initData()
        getWeChat()
        startObserver()
    }

    private fun startObserver() {
        mViewModel?.run {
            uiState.observe(this@WeChatNaviFragment, Observer {
                it?.wechatList?.run {
                    if (isNotEmpty()) {
                        for (its in this){
                            if(its.name.contains("鸿洋")){

                            }else if(its.name.contains("郭霖")){

                            }else if(its.name.contains("玉刚说")){

                            }else if(its.name.contains("承香墨影")){

                            }else if(its.name.contains("Android群英传")){

                            }else if(its.name.contains("code小生")){

                            }else if(its.name.contains("谷歌开发者")){

                            }else if(its.name.contains("奇卓社")){

                            }else if(its.name.contains("美团技术团队")){

                            }else if(its.name.contains("GcsSloop")){

                            }else if(its.name.contains("互联网侦察")){

                            }else if(its.name.contains("susion随心")){

                            }else if(its.name.contains("程序亦非猿")){

                            }else if(its.name.contains("Gityuan")){

                            }
                        }
                        wetChats.addAll(this)
                        listAdapter.setList(wetChats)
                        listAdapter.notifyDataSetChanged()
                    }
                }
            })
        }
    }

    private fun getWeChat() {
        mViewModel.getWeChat()
    }

}