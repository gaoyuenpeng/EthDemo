package com.xiaoyuen.ethcompose.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.xiaoyuen.ethcompose.entity.WalletAccount
import com.xiaoyuen.ethcompose.ui.model.AccountRepository

class AccountBuildMnemonicsViewModel(context: Context) : BaseViewModel(context) {

    private val walletAccountLiveData = MutableLiveData<WalletAccount>()//钱包
    private val mnemonicsLiveData = MutableLiveData<String>()//钱包

    private var accountRepository =
        AccountRepository(context, walletAccountLiveData = walletAccountLiveData)

    fun mnemonicsLiveData(): MutableLiveData<String> = mnemonicsLiveData

    init {
        walletAccountLiveData.observe(context as LifecycleOwner, Observer {
            it?.let {
                if (it.isAvailable()) {
                    mnemonicsLiveData.postValue(it.mnemonics)
                }
            }
        })
    }

    //获取钱包
    fun getMnemonics() {
        accountRepository.getWalletAccount()
    }
}