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

    final override fun initLiveData() {
        super.initLiveData()
        viewModel?.loadingData()?.observe(this, Observer {
            loadingModel.value = it
        })
    }

    open fun initView() {}

    @Composable
    abstract fun loadCompose()

    open fun composeLoaded() {}
}