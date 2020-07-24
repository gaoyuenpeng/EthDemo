package com.xiaoyuen.ethcompose.compose

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.clickable
import androidx.ui.layout.*
import androidx.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.xiaoyuen.ethcompose.entity.WalletAccount
import com.xiaoyuen.ethcompose.ui.viewmodel.MainViewModel
import com.xiaoyuen.ethcompose.R


@Preview
@Composable
fun WalletPage() {
    val walletAccount =
        WalletAccount(
            privateKey = "aksdjhflaksjdhfasd",
            publicKey = "asldkaskjdf;lajsdfljas;ldfkj;asldkfj",
            address = "asldkjf;alsjkdf;lajksd;lf",
            mnemonics = "111 222 333 444 555 666 777 888 999 10 11 12",
            balance = 10.3f
        )
    WalletPage(wallet = walletAccount)
}

//首页钱包页面
@Composable
fun WalletPage(wallet: WalletAccount? = null, mainViewModel: MainViewModel? = null) {

    val showDialog = state { false }

    wallet?.let { walletAccount ->

        BoxGray(modifier = Modifier.fillMaxSize()) {
            VerticalScroller {
                Column {
                    DividerTransparent()
                    ValueItemWithCorner(
                        title = stringResource(R.string.address),
                        value = walletAccount.address
                    )
                    DividerTransparent()
                    ValueItemWithCorner(
                        title = stringResource(R.string.balance),
                        value = "${walletAccount.balance} eth"
                    )
                    DividerTransparent()
                    ValueItemWithCorner(
                        title = stringResource(R.string.private_key),
                        value = walletAccount.privateKey
                    )
                    DividerTransparent()
                    ValueItemWithCorner(
                        title = stringResource(R.string.public_key),
                        value = walletAccount.publicKey
                    )
                    DividerTransparent()
                    ValueItemWithCorner(
                        title = stringResource(R.string.mnemonics),
                        value = walletAccount.mnemonics,
                        modifier = Modifier.clickable(onClick = { mainViewModel?.goMnemonics() })
                    )
                    DividerTransparent(height = 50.dp)
                    CommonButton(
                        title = stringResource(R.string.logout),
                        modifier = Modifier.padding(horizontal = 10.dp),
                        onClick = {
                            showDialog.value = true
                        })

                    if (showDialog.value) {
                        AlertDialog(text = stringResource(R.string.confirm_exit_account),
                            onCloseRequest = { showDialog.value = false },
                            cancelButton = stringResource(R.string.no),
                            comfirmButton = stringResource(R.string.sure),
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
