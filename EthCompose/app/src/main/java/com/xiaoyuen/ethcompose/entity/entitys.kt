package com.xiaoyuen.ethcompose.entity

import androidx.compose.Model
import androidx.ui.foundation.TextFieldValue
import com.xiaoyuen.ethcompose.R

data class TabItemModel(
    val name: String,
    val iconNormal: Int,
    val iconSelect: Int,
    val index: Int
)

val HomeTabItems = listOf(
    TabItemModel(
        "交易",
        R.mipmap.icon_tab_transaction_normal,
        R.mipmap.icon_tab_transaction_select,
        0
    ),
    TabItemModel(
        "钱包",
        R.mipmap.icon_tab_wallet_normal,
        R.mipmap.icon_tab_wallet_select,
        1
    )
)

@Model
class TransferAddressModel {
    var address: String = ""
        get() = if (field.isEmpty()) "" else field
}

@Model
class TransferValueModel {
    var amount: String = "0"
        get() = if (field.isEmpty()) "0" else field
}

@Model
class StringValueModel(var value: String = "")

@Model
data class BooleanValueModel(var value: Boolean = false)

@Model
class TextFieldValueValueModel(var value: TextFieldValue)

@Model
class WalletValueModel(var walletAccount: WalletAccount?)
