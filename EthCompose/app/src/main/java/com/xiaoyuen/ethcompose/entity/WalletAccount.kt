package com.xiaoyuen.ethcompose.entity

import org.web3j.crypto.Credentials

//钱包类
class WalletAccount(
    var privateKey: String,//私钥
    var publicKey: String,//公钥
    var address: String,//钱包地址
    var mnemonics: String,//助记词
    var balance: Float = 0f//余额
) {

    constructor(credentials: Credentials, mnemonics: String) :
            this(
                privateKey = credentials.ecKeyPair.privateKey.toString(16),
                publicKey = credentials.ecKeyPair.publicKey.toString(16),
                address = credentials.address,
                mnemonics = mnemonics
            )

    fun isAvailable(): Boolean {
        return privateKey.isNotEmpty()
    }

}