package com.mg.axechen.wanandroid.ui.article

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mg.axechen.wanandroid.model.ArticleBean
import kotlinx.coroutines.flow.Flow

class ArticleRepository2 {
    fun getArticleData(): Flow<PagingData<ArticleBean>> {
        return Pager(PagingConfig(pageSize = 20)){
            PagedKeyArticleDataSource()
        }.flow
    }
}