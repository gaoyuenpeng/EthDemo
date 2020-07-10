package com.xiaoyuen.ethcompose.ui.viewmodel

import android.app.Activity
import android.content.Context
import com.tbruyelle.rxpermissions.RxPermissions
import android.Manifest;
import com.xiaoyuen.ethcompose.ui.model.WalletAccountRepository


class SplashViewModel(context: Context) : BaseViewModel(context) {

    private var walletAccountRepository = WalletAccountRepository(context)

    //获取钱包
    fun checkAccount() {
        val account = walletAccountRepository.getWalletAccount()
        if (account != null && account.isAvailable()) {
            goMain()
        }
    }

    //请求权限
    fun requestPermissions() {
        RxPermissions(context as Activity).request(
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).subscribe {
            checkAccount()
        }
    }

}