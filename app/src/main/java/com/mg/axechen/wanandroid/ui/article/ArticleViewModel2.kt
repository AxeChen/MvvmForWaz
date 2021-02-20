package com.mg.axechen.wanandroid.ui.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mg.axechen.wanandroid.base.mvvm.BaseViewModel

class ArticleViewModel2: BaseViewModel() {
    private val repository2 by lazy { ArticleRepository2() }

    fun getArticleData() = repository2.getArticleData().asLiveData().cachedIn(viewModelScope)
}