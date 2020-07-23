package com.xiaoyuen.ethcompose.ui.viewmodel

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.ui.res.stringResource
import com.tbruyelle.rxpermissions.RxPermissions
import com.xiaoyuen.ethcompose.R
import com.xiaoyuen.ethcompose.entity.WalletAccount
import com.xiaoyuen.ethcompose.scan.ScanCodeActivity
import com.xiaoyuen.ethcompose.scan.ScanResultModel
import com.xiaoyuen.ethcompose.ui.model.AccountRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.math.BigDecimal


class TransferViewModel(context: Context) : BaseViewModel(context) {

    companion object {
        const val requestScan = 101
    }

    private var permission: RxPermissions? = null

    private val walletAccountLiveData = MutableLiveData<WalletAccount>()//钱包
    private val addressData = MutableLiveData<String>()//转账地址
    private val amountData = MutableLiveData<String>()//转账金额
    private val transferResultData = MutableLiveData<Boolean>()//转账结果

    private var accountRepository =
        AccountRepository(context, walletAccountLiveData = walletAccountLiveData)

    fun walletAccount(): MutableLiveData<WalletAccount> {
        return walletAccountLiveData
    }

    fun addressData(): MutableLiveData<String> {
        return addressData
    }

    fun amountData(): MutableLiveData<String> {
        return amountData
    }

    fun transferResultData(): MutableLiveData<Boolean> {
        return transferResultData
    }

    //获取钱包
    fun getWalletAccount() {
        accountRepository.getWalletAccount()
    }

    //转账
    fun transfer(address: String?, amount: String?) {

        if (address == null || address.isEmpty()) {
            toastMsgData?.postValue(context.getString(R.string.transfer_address_is_empty))
            return
        }

        if (amount == null || amount.isEmpty()) {
            toastMsgData?.postValue(context.getString(R.string.please_input_the_transfer_amount))
            return
        }

        try {

            val value = BigDecimal(amount)

            loadingData.postValue(true)

            GlobalScope.launch {

                val transactionReceipt = accountRepository.transferEth1(address, value)

                loadingData.postValue(false)
                if (transactionReceipt != null) {
                    toastMsgData.postValue(context.getString(R.string.transfer_success))
                    transferResultData.postValue(true)
                } else {
                    toastMsgData.postValue(context.getString(R.string.transfer_address_is_empty))
                    transferResultData.postValue(false)
                }

            }

        } catch (e: Exception) {
            e.printStackTrace()
            loadingData.postValue(false)
            toastMsgData?.postValue(context.getString(R.string.transfer_failure))
        }

    }

    //去扫描二维码
    fun goScan() {

        if (permission == null) permission = RxPermissions(context as Activity)

        //获取照相机权限
        permission!!.request(Manifest.permission.CAMERA).subscribe { granted ->
            if (granted) {
                val intent = Intent(context, ScanCodeActivity::class.java)
                (context as Activity).startActivityForResult(intent, requestScan)
            }
        }

    }

    //扫描二维码回调
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                requestScan -> {//扫描二维码回调
                    data?.let { intent ->
                        val qrCode = intent.getStringExtra("result")
                        qrCode?.let { qrCode ->
                            val scanResult = ScanResultModel(qrCode)
                            scanResult?.let { scanResult ->
                                //更新扫描到的 钱包地址和转账金额
                                scanResult.address?.let {
                                    addressData.postValue(scanResult.address)
                                }
                                scanResult.value?.let {
                                    amountData.postValue(scanResult.value)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}