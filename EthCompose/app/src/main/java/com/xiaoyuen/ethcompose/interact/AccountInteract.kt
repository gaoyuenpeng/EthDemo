package com.xiaoyuen.ethcompose.interact

import android.content.Context
import android.os.Environment
import com.xiaoyuen.ethcompose.R
import com.xiaoyuen.ethcompose.entity.WalletAccount
import com.xiaoyuen.ethcompose.ui.model.WalletAccountRepository
import io.github.novacrypto.bip39.MnemonicGenerator
import io.github.novacrypto.bip39.SeedCalculator
import io.github.novacrypto.bip39.Words
import io.github.novacrypto.bip39.wordlists.English
import io.github.novacrypto.hashing.Sha256
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.web3j.crypto.*
import org.web3j.protocol.Web3jFactory
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.protocol.http.HttpService
import org.web3j.tx.Transfer
import org.web3j.utils.Convert
import java.io.File
import java.io.IOException
import java.math.BigDecimal
import java.security.SecureRandom

class AccountInteract(
    val context: Context,
    private var loginAccountListener: OnLoginAccountListener? = null,
    private var logOutAccountListener: OnLogOutAccountListener? = null,
    private var transferListener: OnTransferListener? = null,
    private var refreshAccountListener: OnRefreshAccountListener? = null,
    private var buildAccountListener: OnBuildAccountListener? = null
) {

    //infura ： Ropsten测试环境
    private val testEthService = "https://ropsten.infura.io/v3/87e351dc69004a02b9cc7653484d0737"

    private val repository = WalletAccountRepository(context)

    private var walletAccount: WalletAccount? = null

    /***
     * 登出
     */
    fun logOut() {
        repository.clearAccount()
        logOutAccountListener?.onLogOutAccountResponse()
    }

    /***
     * 登录
     */
    fun login(mnemonics: String?, passWord: String?, passWordConfirm: String?) {
        if (mnemonics == null || mnemonics.isEmpty()) {
            loginAccountListener?.onLoginAccountResponse(false, null, "请输入12个助记词")
            return
        }

        val mnemonics = mnemonics.trim()
        val mnemonicsList = mnemonics.trim().split(" ")
        if (mnemonicsList.size != 12) {
            loginAccountListener?.onLoginAccountResponse(false, null, "请输入12个助记词")
            return
        }

        if (passWord == null || passWord.isEmpty()) {
            loginAccountListener?.onLoginAccountResponse(false, null, "请输入密码")
            return
        }

        if (passWordConfirm == null || passWordConfirm.isEmpty()) {
            loginAccountListener?.onLoginAccountResponse(false, null, "请重复输入密码")
            return
        }

        if (passWord != passWordConfirm) {
            loginAccountListener?.onLoginAccountResponse(false, null, "密码输入不一致")
            return
        }

        login(mnemonics, passWord)
    }

    /***
     * 登录
     */
    private fun login(mnemonics: String, passWord: String) {

        val seed = SeedCalculator().withWordsFromWordList(English.INSTANCE)
            .calculateSeed(mnemonics.split(" "), passWord)

        GlobalScope.launch {

            val ecKeyPair = ECKeyPair.create(Sha256.sha256(seed))
            val privateKey = ecKeyPair.privateKey.toString(16)
            val publicKey = ecKeyPair.publicKey.toString(16)
            val address = "0x" + Keys.getAddress(publicKey);

            walletAccount = WalletAccount(
                privateKey = privateKey,
                publicKey = publicKey,
                address = address,
                mnemonics = mnemonics
            )
            getBalance(walletAccount!!)
        }
    }

    /***
     * 获取余额
     */
    private fun getBalance(walletAccount: WalletAccount) {
        GlobalScope.launch {

            val web3j = Web3jFactory.build(HttpService(testEthService))
            try {
                val ethGetBalance =
                    web3j.ethGetBalance(walletAccount.address, DefaultBlockParameterName.LATEST)
                        .send()
                val balance = Convert.fromWei(ethGetBalance.balance.toString(), Convert.Unit.ETHER)
                walletAccount.balance = balance.toFloat()
                repository.setWalletAccount(walletAccount)
                loginAccountListener?.onLoginAccountResponse(true, walletAccount, "恢复身份成功")

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /***
     * 刷新余额
     */
    fun refreshBalance() {
        if (walletAccount == null) {
            walletAccount = repository.getWalletAccount()
        }
        if (walletAccount == null) return

        GlobalScope.launch {

            val web3j = Web3jFactory.build(HttpService(testEthService))

            try {
                val ethGetBalance =
                    web3j.ethGetBalance(walletAccount!!.address, DefaultBlockParameterName.LATEST)
                        .send()
                val balance = Convert.fromWei(ethGetBalance.balance.toString(), Convert.Unit.ETHER)
                walletAccount!!.balance = balance.toFloat()
                repository.setWalletAccountBalance(balance.toFloat())
                refreshAccountListener?.onAccountRefreshResponse(walletAccount)

            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /***
     * 创建身份
     */
    fun buildAccount(name: String?, password: String?, passwordConfirm: String?) {
        if (name == null || name.isEmpty()) {
            buildAccountListener?.onBuildAccountResponse(false, null, "身份名不能为空")
            return
        }

        if (password == null || password.isEmpty()) {
            buildAccountListener?.onBuildAccountResponse(false, null, "密码不能为空")
            return
        }

        if (passwordConfirm == null || passwordConfirm.isEmpty()) {
            buildAccountListener?.onBuildAccountResponse(false, null, "重复密码不能为空")
            return
        }

        if (password != passwordConfirm) {
            buildAccountListener?.onBuildAccountResponse(false, null, "密码输入不一致")
            return
        }

        buildAccount(password)
    }

    /***
     * 创建身份
     */
    private fun buildAccount(password: String) {

        val mnemonics = buildMnemonics()
        val seed = MnemonicUtils.generateSeed(mnemonics, password)
        val privateKey = ECKeyPair.create(Hash.sha256(seed))

        val fileDir = File(
            Environment.getExternalStorageDirectory().path,
            context.getString(R.string.app_name)
        )

        if (!fileDir.exists()) {
            fileDir.mkdirs()
        }

        try {
            val walletFile = WalletUtils.generateWalletFile(password, privateKey, fileDir, false)
            walletFile?.let {
                val bip39Wallet = Bip39Wallet(walletFile, mnemonics)
                val credential = WalletUtils.loadBip39Credentials(password, bip39Wallet.mnemonic)
                credential?.let {
                    val accountWallet = WalletAccount(credential, mnemonics)
                    repository.setWalletAccount(accountWallet)
                    buildAccountListener?.onBuildAccountResponse(true, accountWallet, "创建成功")
                    return
                }
            }
        } catch (e: CipherException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        buildAccountListener?.onBuildAccountResponse(false, null, "error")

    }

    /***
     * 转账
     */
    fun transfer(toAddress: String?, amount: String?) {
        if (amount != null && amount.isNotEmpty()) {
            try {
                val value = BigDecimal(amount)
                transfer(toAddress, value)
                return
            } catch (e: Exception) {
            }
        }
        transferListener?.onTransferResponse(false, null, "转账金额输入有误")
    }

    /***
     * 转账
     */
    fun transfer(toAddress: String?, amount: BigDecimal?) {
        if (toAddress == null || toAddress.isEmpty()) {
            transferListener?.onTransferResponse(false, null, "转账地址为空")
            return
        }
        if (amount == null || amount <= BigDecimal(0)) {
            transferListener?.onTransferResponse(false, null, "转账金额有误")
            return
        }
        transferEth(toAddress, amount)
    }

    /***
     * 转账
     */
    private fun transferEth(toAddress: String, amount: BigDecimal) {

        val web3j = Web3jFactory.build(HttpService(testEthService))

        val accountWallet = repository.getWalletAccount()
        //转出账户
        val credential = Credentials.create(accountWallet!!.privateKey)

        GlobalScope.launch {

            try {
                var transactionReceipt =
                    Transfer.sendFunds(web3j, credential, toAddress, amount, Convert.Unit.ETHER)
                        .send()
                transferListener?.onTransferResponse(true, transactionReceipt, "转账申请已提交")
            } catch (e: Exception) {
                transferListener?.onTransferResponse(false, null, "转账失败")
            }
        }
    }

    /***
     * 创建12助记词
     */
    private fun buildMnemonics(): String {
        val mnemonicsSb = StringBuilder()
        val entropy = ByteArray(Words.TWELVE.byteLength())
        SecureRandom().nextBytes(entropy)
        MnemonicGenerator(English.INSTANCE).createMnemonic(entropy) { mnemonicsSb.append(it) }
        return mnemonicsSb.toString()
    }

    /***
     * 登录回调接口
     */
    interface OnLoginAccountListener {
        fun onLoginAccountResponse(
            result: Boolean,
            account: WalletAccount?,
            tip: String
        )
    }

    /***
     * 登出回调接口
     */
    interface OnLogOutAccountListener {
        fun onLogOutAccountResponse()
    }

    /***
     * 刷新身份回调接口
     */
    interface OnRefreshAccountListener {
        fun onAccountRefreshResponse(account: WalletAccount?)
    }

    /***
     * 创建回调接口
     */
    interface OnBuildAccountListener {
        fun onBuildAccountResponse(result: Boolean, account: WalletAccount?, tip: String)
    }

    /***
     * result: 发送请求结果
     * transactionReceipt： 转账结果
     * tip: toast文案
     */
    interface OnTransferListener {
        fun onTransferResponse(
            result: Boolean,
            transactionReceipt: TransactionReceipt?,
            tip: String
        )
    }
}