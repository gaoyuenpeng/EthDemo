package com.xiaoyuen.ethcompose.ui.viewmodel

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.tbruyelle.rxpermissions.RxPermissions
import com.xiaoyuen.ethcompose.entity.WalletAccount
import com.xiaoyuen.ethcompose.interact.AccountInteract
import com.xiaoyuen.ethcompose.scan.ScanCodeActivity
import com.xiaoyuen.ethcompose.scan.ScanResultModel
import com.xiaoyuen.ethcompose.ui.model.WalletAccountRepository
import org.web3j.protocol.core.methods.response.TransactionReceipt


class TransferViewModel(context: Context) : BaseViewModel(context),
    AccountInteract.OnTransferListener {

    companion object {
        const val requestScan = 101
    }

    private var permission: RxPermissions? = null

    private val walletAccount = MutableLiveData<WalletAccount>()//钱包
    private val addressData = MutableLiveData<String>()//转账地址
    private val amountData = MutableLiveData<String>()//转账金额
    private val transferResultData = MutableLiveData<Boolean>()//转账结果

    fun walletAccount(): MutableLiveData<WalletAccount> {
        return walletAccount
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

    private var walletAccountRepository = WalletAccountRepository(context)

    private var accountInteract: AccountInteract? = null

    //获取钱包
    fun getWalletAccount() {
        val account = walletAccountRepository.getWalletAccount()
        account?.let {
            if (account.isAvailable()) {
                walletAccount.postValue(account)
            }
        }
    }

    //转账
    fun transfer(address: String?, amount: String?) {
        loadingData.postValue(true)
        if (accountInteract == null) {
            accountInteract = AccountInteract(context, transferListener = this)
        }
        accountInteract!!.transfer(address, amount)
    }

    //去扫描二维码
    fun goScan() {

        if (permission == null) {
            permission = RxPermissions(context as Activity)
        }

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

    //转账结果回调
    override fun onTransferResponse(
        result: Boolean,
        transactionReceipt: TransactionReceipt?,
        tip: String
    ) {
        loadingData.postValue(false)
        toastMsgData.postValue(tip)
        if (result) {
            if (transactionReceipt != null) {//转账成功
                transferResultData.postValue(true)
            }
        }
    }
}