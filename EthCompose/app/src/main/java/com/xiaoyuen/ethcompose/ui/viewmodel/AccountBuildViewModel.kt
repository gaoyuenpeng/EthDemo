package com.xiaoyuen.ethcompose.ui.viewmodel

import android.Manifest
import android.app.Activity
import android.content.Context
import com.tbruyelle.rxpermissions.RxPermissions
import com.xiaoyuen.ethcompose.entity.WalletAccount
import com.xiaoyuen.ethcompose.interact.AccountInteract


class AccountBuildViewModel(context: Context) : BaseViewModel(context),
    AccountInteract.OnBuildAccountListener {

    private var accountInteract = AccountInteract(context = context, buildAccountListener = this)

    private var permission: RxPermissions? = null

    //创建身份
    fun buildAccount(name: String?, password: String?, passwordConfirm: String?) {

        if (permission == null) {
            permission = RxPermissions(context as Activity)
        }

        //获取手机存储权限
        permission!!.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe { granted ->
            if (granted) {
                loadingData.postValue(true)
                accountInteract.buildAccount(name, password, passwordConfirm)
            }
        }
    }

    //创建身份回调
    override fun onBuildAccountResponse(result: Boolean, account: WalletAccount?, tip: String) {
        loadingData.postValue(false)
        toastMsgData.postValue(tip)

        account?.let {
            if (result && account.isAvailable()) {
                goMnemonics(account.mnemonics)
                if (context is Activity) {
                    context.finish()
                }
            }
        }
    }

}