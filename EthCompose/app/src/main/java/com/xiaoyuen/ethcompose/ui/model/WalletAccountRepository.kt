package com.xiaoyuen.ethcompose.ui.model

import android.content.Context
import com.xiaoyuen.ethcompose.entity.WalletAccount
import com.xiaoyuen.ethcompose.util.GsonUtil
import com.xiaoyuen.ethcompose.util.SharedUtil

class WalletAccountRepository(val context: Context) {

    private val keyAccount = "KeyWalletAccount"
    private var walletAccount: WalletAccount? = null

    init {
        val accountString = SharedUtil.getString(context, keyAccount)
        accountString?.let {
            walletAccount = GsonUtil.formJson(accountString, WalletAccount::class.java)
        }
    }

    fun getWalletAccount(): WalletAccount? {
        return walletAccount
    }

    fun setWalletAccount(account: WalletAccount) {
        this.walletAccount = account
        saveWalletAccount(account)
    }

    fun setWalletAccountBalance(balance: Float) {
        walletAccount?.let {
            it.balance = balance
            saveWalletAccount(it)
        }
    }

    fun clearAccount() {
        SharedUtil.remove(context, keyAccount)
    }

    private fun saveWalletAccount(account: WalletAccount) {
        val value = GsonUtil.toString(account)
        SharedUtil.putString(context, keyAccount, value)
    }
}