package com.mg.axechen.wanandroid.ui.guide

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.model.ProjectKind

class NaviAdapter(datas: MutableList<ProjectKind>) :
    BaseQuickAdapter<ProjectKind, BaseViewHolder>(R.layout.item_onborad_project, datas) {

    override fun convert(holder: BaseViewHolder, item: ProjectKind) {
        holder.setText(R.id.projectTitle, item.name)
    }

}