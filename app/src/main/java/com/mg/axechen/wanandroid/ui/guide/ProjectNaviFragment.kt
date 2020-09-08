package com.mg.axechen.wanandroid.ui.guide

import androidx.lifecycle.Observer
import com.google.android.flexbox.FlexboxLayoutManager
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.base.mvvm.BaseVMFragment
import com.mg.axechen.wanandroid.model.ProjectKind
import kotlinx.android.synthetic.main.fragment_onboard_nav.*

class ProjectNaviFragment : BaseVMFragment<ProjectNaviViewModel>() {

    private var projectKinds: MutableList<ProjectKind> = mutableListOf()

    override fun setLayoutId(): Int = R.layout.fragment_onboard_nav

    private val listAdapter by lazy { NaviAdapter(projectKinds) }


    override fun initView() {
        super.initView()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        rvList.run {
            layoutManager = FlexboxLayoutManager(activity)
            adapter = listAdapter
            listAdapter.setOnItemClickListener { adapter, view, position ->
                var projectKind = projectKinds[position]
                if (listAdapter.selectIndexs.contains(projectKind.id)) {
                    listAdapter.selectIndexs.remove(projectKind.id)
                } else {
                    listAdapter.selectIndexs.add(projectKind.id)
                }
                listAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun startObserver() {
        mViewModel.run {
            uiState.observe(this@ProjectNaviFragment, Observer {
                it.projectList?.run {
                    if (isNotEmpty()) {
                        projectKinds.addAll(this)
                        listAdapter.setList(projectKinds)
                    }
                }
            })
        }
    }


    override fun initData() {
        super.initData()
        getProjectTree()
        startObserver()
    }

    private fun getProjectTree() {
        mViewModel.getProjectKind()
    }


}