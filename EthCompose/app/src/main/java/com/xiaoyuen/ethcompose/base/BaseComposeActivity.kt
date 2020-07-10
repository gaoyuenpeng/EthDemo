package com.xiaoyuen.ethcompose.base

import androidx.compose.Composable
import androidx.lifecycle.Observer
import androidx.ui.core.setContent
import com.xiaoyuen.ethcompose.entity.BooleanValueModel
import com.xiaoyuen.ethcompose.ui.viewmodel.BaseViewModel

abstract class BaseComposeActivity<VM : BaseViewModel> : BaseActivity<VM>() {

    protected val loadingModel = BooleanValueModel()

    final override fun loadView() {
        initView()
        setContent {
            loadCompose()
        }
        composeLoaded()
    }

    /***
     * 设置Compose的liveData：loading
     */
    final override fun initLiveData() {
        super.initLiveData()
        viewModel?.loadingData()?.observe(this, Observer {
            loadingModel.value = it
        })
    }

    open fun initView() {}

    /***
     * compose 布局
     */
    @Composable
    abstract fun loadCompose()

    /***
     * compose加载完成后，实现业务逻辑
     */
    open fun composeLoaded() {}
}