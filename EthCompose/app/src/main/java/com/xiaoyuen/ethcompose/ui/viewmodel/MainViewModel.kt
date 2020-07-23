package com.xiaoyuen.ethcompose.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.xiaoyuen.ethcompose.base.Config
import com.xiaoyuen.ethcompose.entity.WalletAccount
import com.xiaoyuen.ethcompose.ui.model.AccountRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainViewModel(context: Context) : BaseViewModel(context) {

    private val walletAccountLiveData = MutableLiveData<WalletAccount>()

    fun walletAccount(): MutableLiveData<WalletAccount> = walletAccountLiveData

    private val accountRepository =
        AccountRepository(context, walletAccountLiveData = walletAccountLiveData)

    //获取钱包
    fun getWalletAccount() {
        accountRepository.getWalletAccount()
    }

    //更新钱包余额
    fun refreshBalance() {

        GlobalScope.launch {

            val balance = accountRepository.getBalance()
            if (balance > Config.balanceEmpty) {
                accountRepository.setWalletAccountBalance(balance)
                accountRepository.getWalletAccount()
            }

        }
    }

    //登出
    fun logOut() {
        accountRepository.clearAccount()
        goSplash()
    }

}