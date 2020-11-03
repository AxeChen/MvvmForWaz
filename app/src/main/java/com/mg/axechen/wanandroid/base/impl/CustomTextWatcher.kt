package com.mg.axechen.wanandroid.base.impl

import android.text.Editable
import android.text.TextWatcher

abstract class CustomTextWatcher :TextWatcher{
    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

}