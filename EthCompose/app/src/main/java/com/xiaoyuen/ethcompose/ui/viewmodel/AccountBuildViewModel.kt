package com.xiaoyuen.ethcompose.ui.viewmodel

import android.Manifest
import android.app.Activity
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.tbruyelle.rxpermissions.RxPermissions
import com.xiaoyuen.ethcompose.entity.RequestResult
import com.xiaoyuen.ethcompose.entity.WalletAccount
import com.xiaoyuen.ethcompose.ui.model.AccountRepository


class AccountBuildViewModel(context: Context) : BaseViewModel(context) {

    private val buildAccountRequestResultLiveData =
        MutableLiveData<RequestResult<WalletAccount>>()//转账请求结果

    private val accountRepository = AccountRepository(
        context,
        buildAccountRequestResultLiveData = buildAccountRequestResultLiveData
    )

    private var permission: RxPermissions? = null

    init {

        buildAccountRequestResultLiveData.observe(context as LifecycleOwner, Observer {
            loadingData.postValue(false)
            toastMsgData.postValue(it.message)
            if (it.code == 0 && it.result && it.data != null && it.data.isAvailable()) {
                goMnemonics()
                if (context is Activity) {
                    context.finish()
                }
            }
        })

    }

    //创建身份
    fun buildAccount(name: String?, password: String?, passwordConfirm: String?) {
        if (permission == null) permission = RxPermissions(context as Activity)

        //获取手机存储权限
        permission!!.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe { granted ->
            if (granted) {
                build(name, password, passwordConfirm)
            }
        }
    }

    //创建身份
    private fun build(name: String?, password: String?, passwordConfirm: String?) {
        if (name == null || name.isEmpty()) {
            toastMsgData.postValue("身份名不能为空")
            return
        }

        if (password == null || password.isEmpty()) {
            toastMsgData.postValue("密码不能为空")
            return
        }

        if (passwordConfirm == null || passwordConfirm.isEmpty()) {
            toastMsgData.postValue("重复密码不能为空")
            return
        }

        if (password != passwordConfirm) {
            toastMsgData.postValue("密码输入不一致")
            return
        }

        loadingData.postValue(true)
        accountRepository.buildAccount(password)
    }

}