package com.mg.axechen.wanandroid.ui.guide

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mg.axechen.wanandroid.api.repository.WeChatRepository
import com.mg.axechen.wanandroid.api.response.ResultResponse
import com.mg.axechen.wanandroid.base.mvvm.BaseViewModel
import com.mg.axechen.wanandroid.model.ProjectKind
import com.mg.axechen.wanandroid.model.WeChatBean

class WetChatNaviViewModel : BaseViewModel() {

    val weChatRepository by lazy { WeChatRepository() }

    private val _uiState = MutableLiveData<WeChatUIViewModel>()
    val uiState: LiveData<WeChatUIViewModel>
        get() = _uiState

    data class WeChatUIViewModel(
        var wechatList: MutableList<ProjectKind>?
    )

    private fun emitUiState(wechatList: MutableList<ProjectKind>? = null) {
        _uiState.value = WeChatUIViewModel(wechatList)
    }

    fun getWeChat() {
        launch {
            executeResponseList(weChatRepository.getWeChatGroups()).let {
                when (it) {
                    is ResultResponse.SuccessList -> {
                        emitUiState(wechatList = it.data)
                    }
                    is ResultResponse.Error -> {
                        emitUiState(wechatList = mutableListOf())
                    }
                }
            }
        }
    }

}