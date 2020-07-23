package com.xiaoyuen.ethcompose.ui.view

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.input.KeyboardType
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.text.TextRange
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.xiaoyuen.ethcompose.base.BaseComposeActivity
import com.xiaoyuen.ethcompose.compose.*
import com.xiaoyuen.ethcompose.ui.viewmodel.AccountLoginViewModel
import com.xiaoyuen.ethcompose.ui.viewmodel.ViewModelFactory


class AccountLoginActivity : BaseComposeActivity<AccountLoginViewModel>() {

    override fun initViewModel(): AccountLoginViewModel? = ViewModelFactory.buildForLogin(this)

    @Preview
    @Composable
    override fun loadCompose() {

//        val helpWordsState =
//            state { TextFieldValue("") }
//        val passwordState = state { TextFieldValue("") }
//        val passwordConfirmState = state { TextFieldValue("") }

        val helpWordsState =
            state { TextFieldValue("when work aspect fat flush must tilt south summer column safe update") }
        val passwordState = state { TextFieldValue("gao251977337") }
        val passwordConfirmState = state { TextFieldValue("gao251977337") }


        CommonContent("恢复身份",
            onBackClick = { finish() }) {

            Stack(modifier = Modifier.fillMaxSize()) {

                Column(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp, vertical = 10.dp)
                ) {
                    TextBold18("助记词")
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
                        elevation = 0.dp
                    ) {
                        TextFieldWithHint(
                            modifier = Modifier.defaultMinSizeConstraints(minHeight = 80.dp)
                                .padding(10.dp),
                            keyboardType = KeyboardType.Text,
                            value = helpWordsState.value,
                            hint = "助记词",
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
                    TextBold18("钱包密码", modifier = Modifier.padding(top = 15.dp))
                    Card(
                        elevation = 0.dp,
                        modifier = Modifier.padding(top = 5.dp)
                    ) {
                        TextFieldWithHint(
                            modifier = Modifier.defaultMinSizeConstraints(minHeight = 40.dp)
                                .fillMaxWidth().padding(10.dp),
                            value = passwordState.value,
                            keyboardType = KeyboardType.Password,
                            hint = "密码",
                            onValueChange = {
                                passwordState.value = it
                            })
                    }
                    TextBold18("重复输入密码", modifier = Modifier.padding(top = 15.dp))
                    Card(
                        elevation = 0.dp,
                        modifier = Modifier.padding(top = 5.dp)
                    ) {
                        TextFieldWithHint(
                            modifier = Modifier.defaultMinSizeConstraints(minHeight = 40.dp)
                                .fillMaxWidth().padding(10.dp),
                            value = passwordConfirmState.value,
                            keyboardType = KeyboardType.Password,
                            hint = "密码",
                            onValueChange = {
                                passwordConfirmState.value = it
                            })
                    }
                    CommonButton(title = "恢复身份",
                        modifier = Modifier.padding(top = 30.dp),
                        onClick = {
                            viewModel?.login(
                                helpWordsState.value.text,
                                passwordState.value.text,
                                passwordConfirmState.value.text
                            )
                        })
                }
                if (loadingModel.value) {
                    LoadingProgressBar()
                }
            }
        }
    }

}