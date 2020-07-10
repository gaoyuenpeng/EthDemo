package com.xiaoyuen.ethcompose.ui.model

import android.content.Context
import com.xiaoyuen.ethcompose.entity.WalletAccount
import com.xiaoyuen.ethcompose.util.GsonUtil
import com.xiaoyuen.ethcompose.util.SharedUtil

class WalletAccountRepository(val context: Context) {

    private val keyAccount = "KeyWalletAccount"
    private var walletAccount: WalletAccount? = null

    init {
        //demo 没有过多考虑数据安全，暂用SharedPreferences存储钱包数据
        val accountString = SharedUtil.getString(context, keyAccount)
        accountString?.let {
            walletAccount = GsonUtil.formJson(accountString, WalletAccount::class.java)
        }
    }

    //获取钱包
    fun getWalletAccount(): WalletAccount? {
        return walletAccount
    }

    //保存钱包
    fun setWalletAccount(account: WalletAccount) {
        this.walletAccount = account
        saveWalletAccount(account)
    }

    //保存余额
    fun setWalletAccountBalance(balance: Float) {
        walletAccount?.let {
            it.balance = balance
            saveWalletAccount(it)
        }
    }

    //请粗钱包
    fun clearAccount() {
        SharedUtil.remove(context, keyAccount)
    }

    //保存钱包 into SharedPreferences
    private fun saveWalletAccount(account: WalletAccount) {
        val value = GsonUtil.toString(account)
        SharedUtil.putString(context, keyAccount, value)
    }
}