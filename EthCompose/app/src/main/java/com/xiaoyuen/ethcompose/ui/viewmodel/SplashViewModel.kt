package com.xiaoyuen.ethcompose.ui.viewmodel

import android.app.Activity
import android.content.Context
import com.tbruyelle.rxpermissions.RxPermissions
import android.Manifest;
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.xiaoyuen.ethcompose.entity.WalletAccount
import com.xiaoyuen.ethcompose.ui.model.AccountRepository


class SplashViewModel(context: Context) : BaseViewModel(context) {

    private val walletAccountLiveData = MutableLiveData<WalletAccount>()//钱包
    private var accountRepository =
        AccountRepository(context, walletAccountLiveData = walletAccountLiveData)

    init {
        walletAccountLiveData.observe(context as LifecycleOwner, Observer {
            it?.let {
                if (it.isAvailable()) {
                    goMain()
                }
            }
        })
    }

    //获取钱包
    fun checkAccount() {

        RxPermissions(context as Activity).request(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).subscribe {
            accountRepository.getWalletAccount()
        }
    }

}