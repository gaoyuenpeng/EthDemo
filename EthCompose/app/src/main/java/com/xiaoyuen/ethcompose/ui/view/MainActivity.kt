package com.xiaoyuen.ethcompose.ui.view

import androidx.compose.Composable
import androidx.compose.state
import androidx.lifecycle.Observer
import androidx.ui.graphics.Color
import androidx.ui.material.*
import androidx.ui.tooling.preview.Preview
import com.xiaoyuen.ethcompose.base.BaseComposeActivity
import com.xiaoyuen.ethcompose.compose.HomeBottomBar
import com.xiaoyuen.ethcompose.compose.MainPage
import com.xiaoyuen.ethcompose.compose.MyThemeColor
import com.xiaoyuen.ethcompose.entity.WalletValueModel
import com.xiaoyuen.ethcompose.ui.viewmodel.MainViewModel
import com.xiaoyuen.ethcompose.ui.viewmodel.ViewModelFactory

class MainActivity : BaseComposeActivity<MainViewModel>() {

    private val mainValueModel = WalletValueModel(null)

    override fun initViewModel(): MainViewModel? = ViewModelFactory.buildForMain(this)

    override fun initView() {
        viewModel?.walletAccount()?.observe(this, Observer {
            mainValueModel.walletAccount = it
        })
    }

    @Preview
    @Composable
    override fun loadCompose() {
        val currentPageState = state { 0 }

        MaterialTheme(colors = MyThemeColor) {
            Surface(color = Color.Gray) {
                Scaffold(bottomAppBar = {
                    HomeBottomBar(currentPageState, it)
                }) {
                    MainPage(currentPageState.value, mainValueModel.walletAccount, viewModel)
                }
            }
        }
    }


    override fun composeLoaded() {
        viewModel?.getWalletAccount()
    }

    override fun onResume() {
        super.onResume()
        viewModel?.refreshBalance()
    }

}

