package com.xiaoyuen.ethcompose.ui.view

import androidx.compose.Composable
import androidx.compose.state
import androidx.lifecycle.Observer
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.core.drawOpacity
import androidx.ui.foundation.*
import androidx.ui.input.KeyboardType
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.res.stringResource
import androidx.ui.text.TextStyle
import androidx.ui.text.style.TextAlign
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.TextUnit
import androidx.ui.unit.dp
import com.xiaoyuen.ethcompose.R
import com.xiaoyuen.ethcompose.base.BaseComposeActivity
import com.xiaoyuen.ethcompose.compose.*
import com.xiaoyuen.ethcompose.entity.StringValueModel
import com.xiaoyuen.ethcompose.ui.viewmodel.CollectionViewModel
import com.xiaoyuen.ethcompose.ui.viewmodel.ViewModelFactory

class CollectionActivity : BaseComposeActivity<CollectionViewModel>() {

    private val addressValueModel = StringValueModel()
    private val qrCodeValueModel = StringValueModel()
    private val collectionValueModel = StringValueModel()

    override fun initViewModel(): CollectionViewModel? = ViewModelFactory.buildForCollection(this)

    override fun initView() {
        viewModel?.walletAccountLiveData()?.observe(this, Observer {
            addressValueModel.value = it.address
            viewModel?.buildQrCode(it.address, collectionValueModel.value)
        })

        viewModel?.qrCodeData()?.observe(this, Observer {
            qrCodeValueModel.value = it
        })
    }

    @Preview
    @Composable
    override fun loadCompose() {

        CommonContent(
            stringResource(R.string.collection),
            backgroundColor = MainBlue,
            onBackClick = { finish() }) {
            qrCode()
            transferAmount()
        }
    }

    @Composable
    private fun qrCode() {
        Card(
            modifier = Modifier.padding(10.dp).fillMaxWidth(),
            elevation = 0.dp
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalGravity = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.scan_to_transfer) + "${collectionValueModel.value} ETH",
                    style = TextStyle(color = TextGreyHint, fontSize = TextUnit.Sp(16)),
                    modifier = Modifier.padding(top = 20.dp)
                )
                DividerTransparent()
                if (qrCodeValueModel.value.isNotEmpty()) {
                    ImageWithQR(
                        context = context,
                        qrCode = qrCodeValueModel.value,
                        width = 200.dp,
                        height = 200.dp
                    )
                }
                Text(
                    text = stringResource(R.string.wallet_address),
                    style = TextStyle(color = TextGreyHint, fontSize = TextUnit.Sp(16)),
                    modifier = Modifier.padding(top = 30.dp)
                )
                Text(
                    text = addressValueModel.value,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(
                        start = 30.dp,
                        end = 30.dp,
                        bottom = 30.dp
                    ),
                    style = TextStyle(color = TextBlack, fontSize = TextUnit.Sp(24))
                )
            }
        }
    }

    @Composable
    private fun transferAmount() {

        val amountState = state { TextFieldValue() }

        Card(
            modifier = Modifier.padding(horizontal = 10.dp).fillMaxWidth()
                .drawOpacity(1f),
            elevation = 0.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp).fillMaxWidth(),
                verticalGravity = Alignment.CenterVertically
            ) {
                TextFieldWithHint(
                    modifier = Modifier.padding(10.dp).weight(1f),
                    value = amountState.value,
                    keyboardType = KeyboardType.Number,
                    hint = stringResource(R.string.collection_amount),
                    onValueChange = {
                        amountState.value = it
                    })
                Button(
                    onClick = {
                        collectionValueModel.value = ""
                        viewModel?.buildQrCode(
                            addressValueModel.value,
                            collectionValueModel.value
                        )
                        amountState.value = TextFieldValue()
                    },
                    backgroundColor = MainGrey,
                    modifier = Modifier.padding(10.dp)
                ) {
                    Text(text = stringResource(R.string.reset), color = TextBlack)
                }
                Button(onClick = {
                    if (amountState.value.text.isNotEmpty()) {
                        collectionValueModel.value = amountState.value.text
                        viewModel?.buildQrCode(
                            addressValueModel.value,
                            collectionValueModel.value
                        )
                    }
                }) {
                    Text(text = stringResource(R.string.setting))
                }
            }
        }
    }

    override fun composeLoaded() {
        viewModel?.getWalletAddress()
    }

}