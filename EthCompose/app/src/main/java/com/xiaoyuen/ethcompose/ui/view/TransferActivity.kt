package com.xiaoyuen.ethcompose.ui.view

import android.content.Intent
import androidx.compose.Composable
import androidx.lifecycle.Observer
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.TextFieldValue
import androidx.ui.input.KeyboardType
import androidx.ui.layout.*
import androidx.ui.material.Card
import androidx.ui.material.IconButton
import androidx.ui.res.imageResource
import androidx.ui.res.stringResource
import androidx.ui.text.TextRange
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.TextUnit
import androidx.ui.unit.dp
import com.xiaoyuen.ethcompose.base.BaseComposeActivity
import com.xiaoyuen.ethcompose.compose.*
import com.xiaoyuen.ethcompose.entity.WalletValueModel
import com.xiaoyuen.ethcompose.R
import com.xiaoyuen.ethcompose.entity.StringValueModel
import com.xiaoyuen.ethcompose.ui.viewmodel.TransferViewModel
import com.xiaoyuen.ethcompose.ui.viewmodel.ViewModelFactory


class TransferActivity : BaseComposeActivity<TransferViewModel>() {

    private val transferAddressModel = StringValueModel()
    private val transferValueModel = StringValueModel()
    private val walletValueModel = WalletValueModel()

    override fun initViewModel(): TransferViewModel? = ViewModelFactory.buildForTransfer(this)

    override fun initView() {
        viewModel?.walletAccount()?.observe(this, Observer {
            walletValueModel.walletAccount = it
        })

        viewModel?.addressData()?.observe(this, Observer {
            transferAddressModel.value = it
        })

        viewModel?.amountData()?.observe(this, Observer {
            transferValueModel.value = it
        })

        viewModel?.transferResultData()?.observe(this, Observer {
            if (it) {
                finish()
            }
        })
    }

    @Preview
    @Composable
    override fun loadCompose() {

        CommonContent(
            stringResource(R.string.transfer_account),
            onBackClick = { finish() }) {

            Stack(modifier = Modifier.fillMaxSize()) {

                Column(modifier = Modifier.fillMaxSize()) {
                    Card(
                        modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp)
                            .fillMaxWidth(),
                        elevation = 0.dp
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .defaultMinSizeConstraints(minHeight = 50.dp),
                            verticalGravity = Alignment.CenterVertically
                        ) {
                            TextFieldWithHint(
                                modifier = Modifier.padding(start = 10.dp, end = 10.dp).weight(1f),
                                value = TextFieldValue(
                                    text = transferAddressModel.value,
                                    selection = TextRange(
                                        transferAddressModel.value.length,
                                        transferAddressModel.value.length
                                    )
                                ),
                                keyboardType = KeyboardType.Password,
                                hint = stringResource(R.string.receiving_address),
                                onValueChange = {
                                    transferAddressModel.value = it.text
                                }
                            )
                            IconButton(onClick = {
                                viewModel?.goScan()
                            }) {
                                Icon(
                                    imageResource(id = R.mipmap.icon_scan),
                                    tint = TextBlack
                                )
                            }
                        }
                    }
                    Card(
                        modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp)
                            .fillMaxWidth(),
                        elevation = 0.dp
                    ) {
                        Stack(
                            modifier = Modifier.fillMaxWidth()
                                .defaultMinSizeConstraints(minHeight = 50.dp)
                        ) {
                            TextBold18(
                                text = stringResource(R.string.balance),
                                modifier = Modifier.gravity(Alignment.CenterStart)
                                    .padding(start = 10.dp)
                            )
                            Text(
                                text = "${walletValueModel.walletAccount?.balance} eth",
                                color = TextBlack,
                                fontSize = TextUnit.Sp(14),
                                modifier = Modifier.gravity(Alignment.CenterEnd)
                                    .padding(end = 10.dp)
                            )
                        }
                    }
                    Card(
                        modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp)
                            .fillMaxWidth(),
                        elevation = 0.dp
                    ) {
                        Row(
                            verticalGravity = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                                .defaultMinSizeConstraints(minHeight = 50.dp)
                        ) {
                            TextBold18(
                                text = stringResource(R.string.transfer_amount),
                                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                            )
                            TextFieldWithHint(
                                modifier = Modifier.padding(end = 10.dp)
                                    .defaultMinSizeConstraints(minWidth = 200.dp),
                                value = TextFieldValue(
                                    text = transferValueModel.value,
                                    selection = TextRange(
                                        transferValueModel.value.length,
                                        transferValueModel.value.length
                                    )
                                ),
                                keyboardType = KeyboardType.Number,
                                hint = "0",
                                textAlign = TextAlign.End,
                                textStyle = TextStyle(
                                    color = TextBlack,
                                    fontSize = TextUnit.Sp(14)
                                ),
                                onValueChange = {
                                    if (it.text.startsWith("00")) {
                                        return@TextFieldWithHint
                                    }
                                    if (it.text.startsWith(".")) {
                                        return@TextFieldWithHint
                                    }
                                    transferValueModel.value = it.text
                                })
                        }
                    }
                    CommonButton(
                        title = stringResource(R.string.transfer_account),
                        modifier = Modifier.padding(start = 10.dp, top = 50.dp, end = 10.dp),
                        onClick = {
                            viewModel?.transfer(
                                transferAddressModel.value,
                                transferValueModel.value
                            )
                        })
                }
                if (loadingModel.value) {
                    LoadingProgressBar()
                }
            }
        }
    }

    override fun composeLoaded() {
        viewModel?.getWalletAccount()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        viewModel?.onActivityResult(requestCode, resultCode, data)
    }

}