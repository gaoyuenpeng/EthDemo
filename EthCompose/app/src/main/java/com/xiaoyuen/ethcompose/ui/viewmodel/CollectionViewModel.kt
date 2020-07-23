package com.xiaoyuen.ethcompose.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.xiaoyuen.ethcompose.entity.WalletAccount
import com.xiaoyuen.ethcompose.scan.QrCodeUtil
import com.xiaoyuen.ethcompose.ui.model.AccountRepository

class CollectionViewModel(context: Context) : BaseViewModel(context) {

    private val walletAccountLiveData = MutableLiveData<WalletAccount>()

    private val qrCodeData = MutableLiveData<String>()

    fun walletAccountLiveData(): MutableLiveData<WalletAccount> = walletAccountLiveData

    fun qrCodeData(): MutableLiveData<String> = qrCodeData

    private val accountRepository =
        AccountRepository(context, walletAccountLiveData = walletAccountLiveData)

    //获取钱包地址
    fun getWalletAddress() {
        accountRepository.getWalletAccount()
    }

    //获取收款二维码
    fun buildQrCode(address: String?, amount: String?) {
        val qrCode = QrCodeUtil.buildColletionString(address, amount)
        qrCodeData.postValue(qrCode)
    }

}