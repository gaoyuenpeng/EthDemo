package com.xiaoyuen.ethcompose.ui.view

import androidx.compose.Composable
import androidx.lifecycle.Observer
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.unit.dp
import com.xiaoyuen.ethcompose.base.BaseComposeActivity
import com.xiaoyuen.ethcompose.compose.*
import com.xiaoyuen.ethcompose.entity.StringListValueModel
import com.xiaoyuen.ethcompose.ui.viewmodel.AccountBuildMnemonicsViewModel
import com.xiaoyuen.ethcompose.ui.viewmodel.ViewModelFactory

class AccountBuildMnemonicsActivity : BaseComposeActivity<AccountBuildMnemonicsViewModel>() {

    private val mnemonicsValueModel = StringListValueModel()

    override fun initViewModel(): AccountBuildMnemonicsViewModel? =
        ViewModelFactory.buildForAccountBuildMnemonics(this)

    override fun initView() {

        viewModel?.mnemonicsLiveData()?.observe(this, Observer { mnemonics ->
            mnemonicsValueModel.value = mnemonics.split(" ")
        })

    }

    @Composable
    override fun loadCompose() {
        CommonContent("备份助记词",
            backgroundColor = Color.White,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
            onBackClick = { finish() }) {
            TextBold18("请按顺序抄写助记词，确保备份正确", modifier = Modifier.padding(top = 10.dp))
            Box(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                backgroundColor = TextGreyF3
            ) {
                mnemonicsValueModel.value?.let {
                    Mnemonics(mnemonicsValueModel.value!!)
                }
            }
            CommonButton(title = "已确认备份",
                modifier = Modifier.padding(top = 30.dp),
                onClick = { viewModel?.goMain() })
        }
    }

    override fun composeLoaded() {
        viewModel?.getMnemonics()
    }

}