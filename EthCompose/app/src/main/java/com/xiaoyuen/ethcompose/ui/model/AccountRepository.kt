package com.xiaoyuen.ethcompose.ui.model

import android.content.Context
import android.os.Environment
import androidx.lifecycle.MutableLiveData
import com.xiaoyuen.ethcompose.R
import com.xiaoyuen.ethcompose.base.Config
import com.xiaoyuen.ethcompose.entity.RequestResult
import com.xiaoyuen.ethcompose.entity.WalletAccount
import com.xiaoyuen.ethcompose.util.GsonUtil
import com.xiaoyuen.ethcompose.util.SharedUtil
import io.github.novacrypto.bip39.MnemonicGenerator
import io.github.novacrypto.bip39.SeedCalculator
import io.github.novacrypto.bip39.Words
import io.github.novacrypto.bip39.wordlists.English
import io.github.novacrypto.hashing.Sha256
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

class AccountRepository(
    val context: Context,
    var walletAccountLiveData: MutableLiveData<WalletAccount>? = null,
    var buildAccountRequestResultLiveData: MutableLiveData<RequestResult<WalletAccount>>? = null
) {

    private var walletAccount: WalletAccount? = null

    init {
        //demo 没有过多考虑数据安全，暂用SharedPreferences存储钱包数据
        initWalletAcount()
    }

    private fun initWalletAcount() {
        val accountString = SharedUtil.getString(context, Config.keyAccount)
        accountString?.let {
            walletAccount = GsonUtil.formJson(accountString, WalletAccount::class.java)
        }
    }

    //获取钱包
    fun getWalletAccount() {
        walletAccountLiveData?.postValue(walletAccount)
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

    //清除钱包
    fun clearAccount() {
        SharedUtil.remove(context, Config.keyAccount)
    }

    //保存钱包 into SharedPreferences
    private fun saveWalletAccount(account: WalletAccount) {
        val value = GsonUtil.toString(account)
        SharedUtil.putString(context, Config.keyAccount, value)
    }

    /***
     * 转账
     */
    suspend fun transferEth(toAddress: String, amount: BigDecimal): TransactionReceipt? {

        if (walletAccount == null) {
            return null
        }
        val web3j = Web3jFactory.build(HttpService(Config.testEthService))

        //转出账户
        val credential = Credentials.create(walletAccount!!.privateKey)

        return Transfer.sendFunds(web3j, credential, toAddress, amount, Convert.Unit.ETHER).send()

    }

    /***
     * 登录
     */
    suspend fun login(mnemonics: String, passWord: String): WalletAccount {

        val seed = SeedCalculator().withWordsFromWordList(English.INSTANCE)
            .calculateSeed(mnemonics.split(" "), passWord)

        val ecKeyPair = ECKeyPair.create(Sha256.sha256(seed))
        val privateKey = ecKeyPair.privateKey.toString(16)
        val publicKey = ecKeyPair.publicKey.toString(16)
        val address = "0x" + Keys.getAddress(publicKey);

        return WalletAccount(
            privateKey = privateKey,
            publicKey = publicKey,
            address = address,
            mnemonics = mnemonics
        )
    }

    /***
     * 获取钱包余额
     */
    suspend fun getBalance(): Float {
        return if (walletAccount != null) {
            getBalance(walletAccount!!)
        } else {
            Config.balanceEmpty
        }
    }

    /***
     * 获取钱包余额
     */
    suspend fun getBalance(walletAccount: WalletAccount): Float {

        val web3j = Web3jFactory.build(HttpService(Config.testEthService))
        val ethGetBalance =
            web3j.ethGetBalance(walletAccount!!.address, DefaultBlockParameterName.LATEST).send()
        val balanceBig = Convert.fromWei(ethGetBalance.balance.toString(), Convert.Unit.ETHER)

        return balanceBig.toFloat()
    }

    /***
     * 创建身份
     */
    fun buildAccount(password: String) {

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
                    setWalletAccount(accountWallet)
                    buildAccountRequestResultLiveData?.postValue(
                        RequestResult(
                            0,
                            true,
                            context.getString(R.string.build_success),
                            accountWallet
                        )
                    )
                    return
                }
            }
        } catch (e: CipherException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        buildAccountRequestResultLiveData?.postValue(RequestResult(-1, false, "失败"))
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
}