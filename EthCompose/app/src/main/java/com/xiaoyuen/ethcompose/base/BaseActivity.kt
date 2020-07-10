package com.xiaoyuen.ethcompose.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.xiaoyuen.ethcompose.ui.viewmodel.BaseViewModel
import com.xiaoyuen.ethcompose.util.ToastUtil

abstract class BaseActivity<VM : BaseViewModel> : AppCompatActivity() {

    protected lateinit var context: Context
    protected var viewModel: VM? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        initLiveData()
        loadView()
    }

    abstract fun loadView()

    /***
     * 设置通用的liveData：toast
     */
    open fun initLiveData() {

        viewModel = initViewModel()

        viewModel?.toastMsgData()?.observe(this, Observer {
            ToastUtil.show(context, it)
        })

    }

    /***
     * 初始化viewModel
     */
    open fun initViewModel(): VM? = null

}