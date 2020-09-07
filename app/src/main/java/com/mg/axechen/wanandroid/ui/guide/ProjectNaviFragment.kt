package com.mg.axechen.wanandroid.ui.guide

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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
        onBoardProject?.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
        }
    }

    private fun startObserver() {
        mViewModel.run {
            uiState.observe(this@ProjectNaviFragment, Observer {
                it.projectList?.run {
                    projectKinds.addAll(this)
                    listAdapter.setList(projectKinds)
                    listAdapter.notifyDataSetChanged()
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