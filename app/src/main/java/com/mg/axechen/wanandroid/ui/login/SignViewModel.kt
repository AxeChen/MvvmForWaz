package com.mg.axechen.wanandroid.ui.login

import androidx.lifecycle.MutableLiveData
import com.mg.axechen.wanandroid.api.repository.UserRepository
import com.mg.axechen.wanandroid.api.response.ResultResponse
import com.mg.axechen.wanandroid.base.mvvm.BaseViewModel
import com.mg.axechen.wanandroid.cache.UserCache

class SignViewModel : BaseViewModel() {

    private val userRepository by lazy { UserRepository() }

    val uiState = MutableLiveData<SignInViewModel>()

    data class SignInViewModel(
        var loginSuccess: Boolean?
    )

    private fun emitUiState(loginSuccess: Boolean? = null) {
        uiState.value = SignInViewModel(loginSuccess)
    }


    fun userLogin(userName: String, userPwd: String) {
        launch {
            executeResponseEntity(userRepository.userLogin(userName, userPwd)).let {
                when (it) {
                    is ResultResponse.SuccessEntity -> {
                        UserCache.isLogin = true
                        emitUiState(loginSuccess = true)
                    }
                    is ResultResponse.Error -> {
                        emitUiState(loginSuccess = false)
                    }
                }
            }
        }
    }
}