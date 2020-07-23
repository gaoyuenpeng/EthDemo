package com.xiaoyuen.ethcompose.ui.viewmodel

import android.content.Context
import androidx.ui.res.stringResource
import com.xiaoyuen.ethcompose.R
import com.xiaoyuen.ethcompose.ui.model.AccountRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class AccountLoginViewModel(context: Context) : BaseViewModel(context) {

    private val accountRepository = AccountRepository(context)

    //登录
    fun login(mnemonics: String?, passWord: String?, passWordConfirm: String?) {

        if (mnemonics == null || mnemonics.isEmpty()) {
            toastMsgData.postValue(context.getString(R.string.please_input_12_mnemonics))
            return
        }

        val mnemonicsList = mnemonics.trim().split(" ")
        if (mnemonicsList.size != 12) {
            toastMsgData.postValue(context.getString(R.string.please_input_12_mnemonics))
            return
        }

        if (passWord == null || passWord.isEmpty()) {
            toastMsgData.postValue(context.getString(R.string.password_cannot_be_empty))
            return
        }

        if (passWordConfirm == null || passWordConfirm.isEmpty()) {
            toastMsgData.postValue(context.getString(R.string.confirm_password_cannot_be_empty))
            return
        }

        if (passWord != passWordConfirm) {
            toastMsgData.postValue(context.getString(R.string.the_password_input_is_inconsistent))
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