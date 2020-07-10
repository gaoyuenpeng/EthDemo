package com.xiaoyuen.ethcompose.ui.view

import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.graphics.Color
import androidx.ui.input.KeyboardType
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.xiaoyuen.ethcompose.base.BaseComposeActivity
import com.xiaoyuen.ethcompose.compose.*
import com.xiaoyuen.ethcompose.ui.viewmodel.AccountBuildViewModel
import com.xiaoyuen.ethcompose.ui.viewmodel.ViewModelFactory

class AccountBuildActivity : BaseComposeActivity<AccountBuildViewModel>() {

    override fun initViewModel(): AccountBuildViewModel? =
        ViewModelFactory.buildForAccountBuild(this)

    @Composable
    override fun loadCompose() {

        val nameState = state { TextFieldValue("gaoyuenpeng123456") }
        val passwordState = state { TextFieldValue("gao123456") }
        val passwordConfirmState = state { TextFieldValue("gao123456") }

        CommonContent("创建身份",
            modifier = Modifier.padding(10.dp),
            onBackClick = { finish() }) {
            Stack(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize()) {
                    TextBold18("身份名")
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
                        elevation = 0.dp
                    ) {
                        TextFieldWithHint(
                            modifier = Modifier.padding(horizontal = 10.dp).height(50.dp)
                                .fillMaxWidth(),
                            value = nameState.value,
                            keyboardType = KeyboardType.Password,
                            hint = "身份名",
                            onValueChange = {
                                nameState.value = it
                            })
                    }
                    TextBold18("密码", modifier = Modifier.padding(top = 15.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
                        elevation = 0.dp
                    ) {
                        TextFieldWithHint(
                            modifier = Modifier.padding(horizontal = 10.dp).height(50.dp)
                                .fillMaxWidth(),
                            value = passwordState.value,
                            keyboardType = KeyboardType.Password,
                            hint = "密码",
                            onValueChange = {
                                passwordState.value = it
                            })
                    }
                    TextBold18("重复密码", modifier = Modifier.padding(top = 15.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
                        elevation = 0.dp
                    ) {
                        TextFieldWithHint(
                            modifier = Modifier.padding(horizontal = 10.dp).height(50.dp)
                                .fillMaxWidth(),
                            value = passwordConfirmState.value,
                            keyboardType = KeyboardType.Password,
                            hint = "重复密码",
                            onValueChange = {
                                passwordConfirmState.value = it
                            })
                    }
                    Divider(color = Color.Transparent, modifier = Modifier.height(30.dp))
                    CommonButton(title = "创建",
                        modifier = Modifier.padding(top = 30.dp),
                        onClick = {
                            viewModel?.buildAccount(
                                nameState.value.text,
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