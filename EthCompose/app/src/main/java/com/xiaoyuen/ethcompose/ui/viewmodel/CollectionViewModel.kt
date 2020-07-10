package com.xiaoyuen.ethcompose.ui.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.xiaoyuen.ethcompose.scan.QrCodeUtil
import com.xiaoyuen.ethcompose.ui.model.WalletAccountRepository

class CollectionViewModel(context: Context) : BaseViewModel(context) {

    private val addressData = MutableLiveData<String>()
    private val qrCodeData = MutableLiveData<String>()

    fun addressData(): MutableLiveData<String> {
        return addressData
    }

    fun qrCodeData(): MutableLiveData<String> {
        return qrCodeData
    }

    private var walletAccountRepository = WalletAccountRepository(context)

    //获取钱包地址
    fun getWalletAddress() {
        val account = walletAccountRepository.getWalletAccount()
        account?.let {
            if (account.isAvailable()) {
                addressData.postValue(account.address)
            }
        }
    }

    //获取收款二维码
    fun buildQrCode(address: String?, amount: String?) {
        val qrCode = QrCodeUtil.buildColletionString(address, amount)
        qrCodeData.postValue(qrCode)
    }

}