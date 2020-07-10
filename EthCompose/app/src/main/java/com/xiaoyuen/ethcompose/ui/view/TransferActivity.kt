package com.xiaoyuen.ethcompose.ui.view

import android.content.Intent
import androidx.compose.Composable
import androidx.lifecycle.Observer
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.TextFieldValue
import androidx.ui.graphics.Color
import androidx.ui.input.KeyboardType
import androidx.ui.layout.*
import androidx.ui.material.Card
import androidx.ui.material.IconButton
import androidx.ui.res.imageResource
import androidx.ui.text.TextRange
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.TextUnit
import androidx.ui.unit.dp
import com.xiaoyuen.ethcompose.base.BaseComposeActivity
import com.xiaoyuen.ethcompose.compose.*
import com.xiaoyuen.ethcompose.entity.TransferAddressModel
import com.xiaoyuen.ethcompose.entity.TransferValueModel
import com.xiaoyuen.ethcompose.entity.WalletValueModel
import com.xiaoyuen.ethcompose.R
import com.xiaoyuen.ethcompose.ui.viewmodel.TransferViewModel
import com.xiaoyuen.ethcompose.ui.viewmodel.ViewModelFactory


class TransferActivity : BaseComposeActivity<TransferViewModel>() {

    private val transferAddressModel = TransferAddressModel()
    private val transferValueModel = TransferValueModel()
    private val walletValueModel = WalletValueModel(null)

    override fun initViewModel(): TransferViewModel? = ViewModelFactory.buildForTransfer(this)

    override fun initView() {
        viewModel?.walletAccount()?.observe(this, Observer {
            walletValueModel.walletAccount = it
        })

        viewModel?.addressData()?.observe(this, Observer {
            transferAddressModel.address = it
        })

        viewModel?.amountData()?.observe(this, Observer {
            transferValueModel.amount = it
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
            "转账",
            backgroundColor = Color(0xFFededed),
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
                                    text = transferAddressModel.address,
                                    selection = TextRange(
                                        transferAddressModel.address.length,
                                        transferAddressModel.address.length
                                    )
                                ),
                                keyboardType = KeyboardType.Text,
                                hint = "收款地址",
                                onValueChange = {
                                    transferAddressModel.address = it.text
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
                                text = "余额",
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
                        Stack(
                            modifier = Modifier.fillMaxWidth()
                                .defaultMinSizeConstraints(minHeight = 50.dp)
                        ) {

                            TextBold18(
                                text = "转账金额",
                                modifier = Modifier.gravity(Alignment.CenterStart)
                                    .padding(start = 10.dp)
                            )
                            TextFieldWithHint(
                                modifier = Modifier.gravity(Alignment.CenterEnd)
                                    .padding(end = 10.dp)
                                    .defaultMinSizeConstraints(minWidth = 200.dp),
                                value = TextFieldValue(
                                    text = transferValueModel.amount,
                                    selection = TextRange(
                                        transferValueModel.amount.length,
                                        transferValueModel.amount.length
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
                                    transferValueModel.amount = it.text
                                })
                        }
                    }
//            Card(
//                modifier = Modifier.padding(start = 10.dp, top = 10.dp, end = 10.dp).fillMaxWidth()
//                    .drawOpacity(0f),
//                elevation = 0.dp
//            ) {
//
//                Stack(
//                    modifier = Modifier.fillMaxWidth().defaultMinSizeConstraints(minHeight = 50.dp)
//                ) {
//                    Text(
//                        text = "矿工费",
//                        color = TextBlack,
//                        fontSize = TextUnit.Sp(18),
//                        modifier = Modifier.gravity(Alignment.CenterStart).padding(start = 10.dp)
//                    )
//                    Text(
//                        text = "${castState.value} eth",
//                        color =TextBlack,
//                        fontSize = TextUnit.Sp(14),
//                        modifier = Modifier.gravity(Alignment.CenterEnd).padding(end = 10.dp)
//                    )
//                }
//            }
                    CommonButton(
                        title = "转账",
                        modifier = Modifier.padding(start = 10.dp, top = 50.dp, end = 10.dp),
                        onClick = {
                            viewModel?.transfer(
                                transferAddressModel.address,
                                transferValueModel.amount
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