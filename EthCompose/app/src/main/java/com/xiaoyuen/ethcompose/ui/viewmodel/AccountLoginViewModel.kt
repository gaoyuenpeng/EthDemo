package com.xiaoyuen.ethcompose.ui.viewmodel

import android.content.Context
import com.xiaoyuen.ethcompose.entity.WalletAccount
import com.xiaoyuen.ethcompose.interact.AccountInteract


class AccountLoginViewModel(context: Context) : BaseViewModel(context),
    AccountInteract.OnLoginAccountListener {

    private var getAccountInteract = AccountInteract(context, loginAccountListener = this)

    //登录
    fun login(helpWords: String?, passWord: String?, passWordConfirm: String?) {
        loadingData.postValue(true)
        getAccountInteract.login(helpWords, passWord, passWordConfirm)
    }

    //登录回调
    override fun onLoginAccountResponse(result: Boolean, account: WalletAccount?, tip: String) {
        loadingData.postValue(false)
        toastMsgData.postValue(tip)
        if (result && account != null && account.isAvailable()) {
            goMain()
        }
    }

}