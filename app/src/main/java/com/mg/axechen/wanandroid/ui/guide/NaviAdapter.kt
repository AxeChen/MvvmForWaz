package com.mg.axechen.wanandroid.ui.guide

import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.mg.axechen.wanandroid.R
import com.mg.axechen.wanandroid.model.ProjectKind

class NaviAdapter(datas: MutableList<ProjectKind>) :
    BaseQuickAdapter<ProjectKind, BaseViewHolder>(R.layout.item_onborad_project, datas) {

    var selectIndexs: MutableList<Int> = mutableListOf()

    override fun convert(holder: BaseViewHolder, item: ProjectKind) {
        holder.setText(R.id.projectTitle, item.name)
        var content = holder.getView<LinearLayout>(R.id.llContent)
        if (selectIndexs.contains(item.id)) {
            content.setBackgroundColor(ContextCompat.getColor(context, R.color.green_38875f))
            holder.setTextColor(R.id.projectTitle, ContextCompat.getColor(context, R.color.white))
        } else {
            holder.setTextColor(
                R.id.projectTitle,
                ContextCompat.getColor(context, R.color.text_title)
            )
            content.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
    }

}