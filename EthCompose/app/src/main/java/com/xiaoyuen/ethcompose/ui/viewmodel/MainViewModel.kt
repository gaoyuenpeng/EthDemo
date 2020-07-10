package com.xiaoyuen.ethcompose.ui.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.xiaoyuen.ethcompose.entity.WalletAccount
import com.xiaoyuen.ethcompose.interact.AccountInteract
import com.xiaoyuen.ethcompose.ui.model.WalletAccountRepository
import com.xiaoyuen.ethcompose.ui.view.SplashActivity


class MainViewModel(context: Context) : BaseViewModel(context),
    AccountInteract.OnLogOutAccountListener,
    AccountInteract.OnRefreshAccountListener {

    private val walletAccount = MutableLiveData<WalletAccount>()

    fun walletAccount(): MutableLiveData<WalletAccount> = walletAccount

    private var accountInteract =
        AccountInteract(
            context = context,
            logOutAccountListener = this,
            refreshAccountListener = this
        )

    private var walletAccountRepository = WalletAccountRepository(context)

    //获取钱包
    fun getWalletAccount() {
        val account = walletAccountRepository.getWalletAccount()
        account?.let {
            if (account.isAvailable()) {
                walletAccount.postValue(account)
            }
        }
    }

    //更新钱包余额
    fun refreshBalance() {
        accountInteract.refreshBalance()
    }

    //登出
    fun logOut() {
        accountInteract.logOut()
    }

    //登出回调
    override fun onLogOutAccountResponse() {
        val intent = Intent(context, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    //更新余额回调
    override fun onAccountRefreshResponse(account: WalletAccount?) {
        walletAccount.postValue(account)
    }

}