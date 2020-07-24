package com.xiaoyuen.ethcompose.ui.view

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.core.drawOpacity
import androidx.ui.foundation.*
import androidx.ui.input.KeyboardType
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.res.stringResource
import androidx.ui.text.TextRange
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.xiaoyuen.ethcompose.R
import com.xiaoyuen.ethcompose.base.BaseComposeActivity
import com.xiaoyuen.ethcompose.base.Config
import com.xiaoyuen.ethcompose.compose.*
import com.xiaoyuen.ethcompose.ui.viewmodel.AccountLoginViewModel
import com.xiaoyuen.ethcompose.ui.viewmodel.ViewModelFactory


class AccountLoginActivity : BaseComposeActivity<AccountLoginViewModel>() {

    override fun initViewModel(): AccountLoginViewModel? = ViewModelFactory.buildForLogin(this)

    @Preview
    @Composable
    override fun loadCompose() {

        val helpWordsState = state { TextFieldValue("") }
        val passwordState = state { TextFieldValue("") }
        val passwordConfirmState = state { TextFieldValue("") }

//        val helpWordsState =
//            state { TextFieldValue("when work aspect fat flush must tilt south summer column safe update") }
//        val passwordState = state { TextFieldValue("gao251977337") }
//        val passwordConfirmState = state { TextFieldValue("gao251977337") }

        CommonContent(stringResource(R.string.restore_account), onBackClick = { finish() }) {

            Stack(modifier = Modifier.fillMaxSize()) {

                Column(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp, vertical = 10.dp)
                ) {
                    TextBold18(stringResource(id = R.string.mnemonics))
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
                        elevation = 0.dp
                    ) {
                        TextFieldWithHint(
                            modifier = Modifier.defaultMinSizeConstraints(minHeight = 80.dp)
                                .padding(10.dp),
                            keyboardType = KeyboardType.Text,
                            value = helpWordsState.value,
                            hint = stringResource(id = R.string.mnemonics),
                            onValueChange = {
                                if (it.text.contains("  ")) {
                                    val value = it.text.replace("  ", " ")
                                    helpWordsState.value = TextFieldValue(
                                        value,
                                        TextRange(value.length, value.length)
                                    )
                                } else {
                                    helpWordsState.value = it
                                }
                            })
                    }
                    TextBold18(
                        stringResource(R.string.wallet_password),
                        modifier = Modifier.padding(top = 15.dp)
                    )
                    Card(
                        elevation = 0.dp,
                        modifier = Modifier.padding(top = 5.dp)
                    ) {
                        TextFieldWithHint(
                            modifier = Modifier.defaultMinSizeConstraints(minHeight = 40.dp)
                                .fillMaxWidth().padding(10.dp),
                            value = passwordState.value,
                            keyboardType = KeyboardType.Password,
                            hint = stringResource(R.string.password),
                            onValueChange = {
                                passwordState.value = it
                            })
                    }
                    TextBold18(
                        stringResource(R.string.wallet_re_password),
                        modifier = Modifier.padding(top = 15.dp)
                    )
                    Card(
                        elevation = 0.dp,
                        modifier = Modifier.padding(top = 5.dp)
                    ) {
                        TextFieldWithHint(
                            modifier = Modifier.defaultMinSizeConstraints(minHeight = 40.dp)
                                .fillMaxWidth().padding(10.dp),
                            value = passwordConfirmState.value,
                            keyboardType = KeyboardType.Password,
                            hint = stringResource(R.string.password),
                            onValueChange = {
                                passwordConfirmState.value = it
                            })
                    }
                    CommonButton(title = stringResource(R.string.restore_account),
                        modifier = Modifier.padding(top = 50.dp),
                        onClick = {
                            viewModel?.login(
                                helpWordsState.value.text,
                                passwordState.value.text,
                                passwordConfirmState.value.text
                            )
                        })
                    CommonButton(title = stringResource(R.string.test_account),
                        modifier = Modifier.padding(top = 30.dp).drawOpacity(0.2f),
                        onClick = {
                            helpWordsState.value = TextFieldValue(Config.testMnemonics)
                            passwordState.value = TextFieldValue(Config.testPassword)
                            passwordConfirmState.value = TextFieldValue(Config.testPassword)
                        })
                }
                if (loadingModel.value) {
                    LoadingProgressBar()
                }
            }
        }
    }

}