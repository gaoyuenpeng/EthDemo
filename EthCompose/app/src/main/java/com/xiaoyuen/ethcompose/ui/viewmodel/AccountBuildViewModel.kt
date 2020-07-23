package com.xiaoyuen.ethcompose.ui.viewmodel

import android.Manifest
import android.app.Activity
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.ui.res.stringResource
import com.tbruyelle.rxpermissions.RxPermissions
import com.xiaoyuen.ethcompose.R
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
            toastMsgData.postValue(context.getString(R.string.account_name_cannot_be_empty))
            return
        }

        if (password == null || password.isEmpty()) {
            toastMsgData.postValue(context.getString(R.string.password_cannot_be_empty))
            return
        }

        if (passwordConfirm == null || passwordConfirm.isEmpty()) {
            toastMsgData.postValue(context.getString(R.string.confirm_password_cannot_be_empty))
            return
        }

        if (password != passwordConfirm) {
            toastMsgData.postValue(context.getString(R.string.the_password_input_is_inconsistent))
            return
        }

        loadingData.postValue(true)
        accountRepository.buildAccount(password)
    }

}