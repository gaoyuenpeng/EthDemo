package com.xiaoyuen.ethcompose.compose

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.*
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.xiaoyuen.ethcompose.entity.WalletAccount
import com.xiaoyuen.ethcompose.ui.viewmodel.MainViewModel

@Preview
@Composable
fun WalletPage() {
    val account =
        WalletAccount(
            privateKey = "aksdjhflaksjdhfasd",
            publicKey = "asldkaskjdf;lajsdfljas;ldfkj;asldkfj",
            address = "asldkjf;alsjkdf;lajksd;lf",
            mnemonics = "111 222 333 444 555 666 777 888 999 10 11 12",
            balance = 10.3f
        )
    WalletPage(wallet = account, mainViewModel = null)
}

@Composable
fun WalletPage(wallet: WalletAccount?, mainViewModel: MainViewModel?) {

    val showDialog = state { false }

    wallet?.let { walletAccount ->

        BoxGray(modifier = Modifier.fillMaxSize()) {
            VerticalScroller {
                Column {
                    DividerTransparent()
                    ValueItemWithCorner(
                        title = "地址",
                        value = walletAccount.address
                    )
                    DividerTransparent()
                    ValueItemWithCorner(
                        title = "余额",
                        value = "${walletAccount.balance} eth"
                    )
                    DividerTransparent()
                    ValueItemWithCorner(
                        title = "私钥",
                        value = walletAccount.privateKey
                    )
                    DividerTransparent()
                    ValueItemWithCorner(
                        title = "公钥",
                        value = walletAccount.publicKey
                    )
                    DividerTransparent()
                    ValueItemWithCorner(
                        title = "助记词",
                        value = walletAccount.mnemonics
                    )
                    DividerTransparent(height = 50.dp)
                    CommonButton(
                        title = "退出登录",
                        modifier = Modifier.padding(horizontal = 10.dp),
                        onClick = {
                            showDialog.value = true
                        })

                    if (showDialog.value) {
                        AlertDialog(text = "确定退出账号？",
                            onCloseRequest = { showDialog.value = false },
                            cancelButton = "暂不",
                            comfirmButton = "确定",
                            cancelButtonRequest = { showDialog.value = false },
                            comfirmButtonRequest = {
                                showDialog.value = false
                                mainViewModel?.logOut()
                            }
                        )
                    }
                }
            }
        }
    }
}
