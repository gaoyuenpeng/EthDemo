package com.xiaoyuen.ethcompose.entity

import androidx.compose.Model
import androidx.ui.foundation.TextFieldValue
import com.xiaoyuen.ethcompose.R

//首页tab
data class TabItemModel(
    val name: String,
    val iconNormal: Int,
    val iconSelect: Int,
    val index: Int
)

//首页tab item
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

//string Model
@Model
class StringValueModel(var value: String = "")

//boolean Model
@Model
data class BooleanValueModel(var value: Boolean = false)

//TextFieldValue Model
@Model
class TextFieldValueValueModel(var value: TextFieldValue)

//WalletAccount Model
@Model
class WalletValueModel(var walletAccount: WalletAccount?)
