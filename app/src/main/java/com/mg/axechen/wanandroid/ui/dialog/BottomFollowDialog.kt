package com.mg.axechen.wanandroid.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mg.axechen.wanandroid.R
import kotlinx.android.synthetic.main.dialog_follow_type.*

/**
 * 底部弹窗Follow提示
 */
class BottomFollowDialog : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_follow_type, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ivFollowCardClose.setOnClickListener { dismissAllowingStateLoss() }
    }


}