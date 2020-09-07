package com.mg.axechen.wanandroid.ui.guide

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mg.axechen.wanandroid.api.repository.ProjectRepository
import com.mg.axechen.wanandroid.api.response.ResultResponse
import com.mg.axechen.wanandroid.base.mvvm.BaseViewModel
import com.mg.axechen.wanandroid.model.ProjectKind

class ProjectNaviViewModel : BaseViewModel() {

    private val projectRepository by lazy { ProjectRepository() }

    private val _uiState = MutableLiveData<ProjectNavModel>()
    val uiState: LiveData<ProjectNavModel>
        get() = _uiState

    data class ProjectNavModel(
        var projectList: MutableList<ProjectKind>?
    )

    private fun emitUiState(projectList: MutableList<ProjectKind>? = null) {
        _uiState.value = ProjectNavModel(projectList)
    }

    fun getProjectKind() {
        launch {
            executeResponseList(projectRepository.getProjectTree()).let {
                when (it) {
                    is ResultResponse.SuccessList -> {
                        emitUiState(it.data)
                    }
                    is ResultResponse.Error -> {
                        emitUiState(projectList = mutableListOf())
                    }
                }
            }
        }
    }
}