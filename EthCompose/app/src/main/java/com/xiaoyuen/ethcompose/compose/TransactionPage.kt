package com.xiaoyuen.ethcompose.compose

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.layout.*
import androidx.ui.material.Card
import androidx.ui.material.MaterialTheme
import androidx.ui.res.imageResource
import androidx.ui.text.TextStyle
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.TextUnit
import androidx.ui.unit.dp
import com.xiaoyuen.ethcompose.entity.WalletAccount
import com.xiaoyuen.ethcompose.ui.viewmodel.MainViewModel
import com.xiaoyuen.ethcompose.R


@Preview
@Composable
fun TransactionPage() {
    val wallet =
        WalletAccount(
            privateKey = "aksdjhflaksjdhfasd",
            publicKey = "asldkaskjdf;lajsdfljas;ldfkj;asldkfj",
            address = "asldkjf;alsjkdf;lajksd;lf",
            mnemonics = "111 222 333 444 555 666 777 888 999 10 11 12",
            balance = 10.3f
        )
    TransactionPage(wallet = wallet, mainViewModel = null)
}

@Composable
fun TransactionPage(wallet: WalletAccount?, mainViewModel: MainViewModel?) {
    wallet?.let { walletAccount ->
        MaterialTheme(colors = MyThemeColor) {
            BoxGray(modifier = Modifier.fillMaxSize()) {
                Card(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                    Stack(modifier = Modifier.height(150.dp)) {
                        Image(
                            asset = imageResource(id = R.mipmap.icon_eth_unit),
                            modifier = Modifier.gravity(Alignment.CenterStart)
                                .padding(start = 10.dp)
                        )
                        Text(
                            text = "${walletAccount.balance} ETH",
                            modifier = Modifier.gravity(Alignment.CenterEnd).padding(end = 20.dp),
                            style = TextStyle(color = TextBlack, fontSize = TextUnit.Sp(24))
                        )
                    }
                }
                DividerTransparent(20.dp)
                CommonButton(
                    title = "收款",
                    modifier = Modifier.padding(horizontal = 10.dp),
                    onClick = { mainViewModel?.goIncome() })
                DividerTransparent()
                CommonButton(
                    title = "转账",
                    modifier = Modifier.padding(horizontal = 10.dp),
                    onClick = { mainViewModel?.goTransfer() })
            }
        }
    }
}