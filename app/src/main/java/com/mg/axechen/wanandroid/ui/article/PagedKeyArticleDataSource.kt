package com.mg.axechen.wanandroid.ui.article

import androidx.paging.PagingSource
import com.mg.axechen.wanandroid.api.repository.ArticleRepository
import com.mg.axechen.wanandroid.model.ArticleBean

class PagedKeyArticleDataSource : PagingSource<Int, ArticleBean>() {

    private val articleRepository by lazy { ArticleRepository() }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ArticleBean> {
        return try {
            val page = params.key ?: 0
            var result = articleRepository.getArticleList(page)
            if (result.errorCode == 0) {
                LoadResult.Page(data = result.data!!.datas, nextKey = page + 1, prevKey = null)
            } else {
                LoadResult.Error(ApiException(code = 11, displayMsg = "未知错误"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

class ApiException(var code: Int, var displayMsg: String) : Throwable()

