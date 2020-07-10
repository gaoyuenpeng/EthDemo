package com.xiaoyuen.ethcompose.ui.view

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.unit.dp
import com.xiaoyuen.ethcompose.base.BaseComposeActivity
import com.xiaoyuen.ethcompose.compose.CommonButton
import com.xiaoyuen.ethcompose.compose.CommonContent
import com.xiaoyuen.ethcompose.compose.Mnemonics
import com.xiaoyuen.ethcompose.compose.TextBold18
import com.xiaoyuen.ethcompose.ui.viewmodel.AccountBuildMnemonicsViewModel
import com.xiaoyuen.ethcompose.ui.viewmodel.ViewModelFactory

class AccountBuildMnemonicsActivity : BaseComposeActivity<AccountBuildMnemonicsViewModel>() {

    var mnemonicsList: List<String>? = null

    override fun initViewModel(): AccountBuildMnemonicsViewModel? =
        ViewModelFactory.buildForAccountBuildMnemonics(this)

    override fun initView() {
        val mnemonics = intent.getStringExtra("mnemonics")
        mnemonics?.let {
            mnemonicsList = mnemonics.split(" ")
        }
    }

    @Composable
    override fun loadCompose() {
        CommonContent("备份助记词",
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp),
            onBackClick = { finish() }) {
            TextBold18("请按顺序抄写助记词，确保备份正确", modifier = Modifier.padding(top = 10.dp))
            Box(
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                backgroundColor = Color(0xFFf3f3f3)
            ) {
                mnemonicsList?.let {
                    Mnemonics(mnemonicsList!!)
                }
            }
            CommonButton(title = "已确认备份",
                modifier = Modifier.padding(top = 30.dp),
                onClick = { viewModel?.goMain() })
        }
    }

}