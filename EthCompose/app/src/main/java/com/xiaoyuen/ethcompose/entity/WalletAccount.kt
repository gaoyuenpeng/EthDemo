package com.xiaoyuen.ethcompose.entity

import org.web3j.crypto.Credentials

//钱包类
class WalletAccount(
    var privateKey: String,
    var publicKey: String,
    var address: String,
    var mnemonics: String,
    var balance: Float = 0f
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