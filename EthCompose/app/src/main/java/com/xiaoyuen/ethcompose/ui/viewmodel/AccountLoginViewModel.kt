package com.xiaoyuen.ethcompose.ui.viewmodel

import android.content.Context
import com.xiaoyuen.ethcompose.ui.model.AccountRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AccountLoginViewModel(context: Context) : BaseViewModel(context) {

    private val accountRepository = AccountRepository(context)

    //登录
    fun login(mnemonics: String?, passWord: String?, passWordConfirm: String?) {

        if (mnemonics == null || mnemonics.isEmpty()) {
            toastMsgData.postValue("请输入12个助记词")
            return
        }

        val mnemonics = mnemonics.trim()
        val mnemonicsList = mnemonics.trim().split(" ")
        if (mnemonicsList.size != 12) {
            toastMsgData.postValue("请输入12个助记词")
            return
        }

        if (passWord == null || passWord.isEmpty()) {
            toastMsgData.postValue("请输入密码")
            return
        }

        if (passWordConfirm == null || passWordConfirm.isEmpty()) {
            toastMsgData.postValue("请重复输入密码")
            return
        }

        if (passWord != passWordConfirm) {
            toastMsgData.postValue("密码输入不一致")
            return
        }

        loadingData.postValue(true)

        GlobalScope.launch {

            val walletAccount = accountRepository.login(mnemonics, passWord)
            val balance = accountRepository.getBalance(walletAccount)
            walletAccount.balance = balance
            accountRepository.setWalletAccount(walletAccount)

            loadingData.postValue(false)

            goMain()
        }
    }

}