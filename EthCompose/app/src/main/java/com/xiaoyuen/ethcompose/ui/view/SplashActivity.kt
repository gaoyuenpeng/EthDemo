package com.xiaoyuen.ethcompose.ui.view

import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.ContentScale
import androidx.ui.core.Modifier
import androidx.ui.foundation.Image
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.res.imageResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import com.xiaoyuen.ethcompose.base.BaseComposeActivity
import com.xiaoyuen.ethcompose.compose.CommonButton
import com.xiaoyuen.ethcompose.compose.CommonContent
import com.xiaoyuen.ethcompose.ui.viewmodel.SplashViewModel
import com.xiaoyuen.ethcompose.ui.viewmodel.ViewModelFactory
import com.xiaoyuen.ethcompose.R

class SplashActivity : BaseComposeActivity<SplashViewModel>() {

    override fun initViewModel(): SplashViewModel? = ViewModelFactory.buildForSplash(this)

    @Preview
    @Composable
    override fun loadCompose() {

        CommonContent(hasTitle = false, backgroundColor = Color.White) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalGravity = Alignment.CenterHorizontally
            ) {
                Image(
                    asset = imageResource(id = R.mipmap.icon_eth_biger),
                    modifier = Modifier.size(150.dp, 150.dp),
                    contentScale = ContentScale.Fit
                )
                CommonButton(
                    title = "恢复身份",
                    modifier = Modifier.width(width = 200.dp).padding(top = 150.dp),
                    onClick = {
                        viewModel?.goLiginAccount()
                    }
                )
                CommonButton(
                    title = "创建身份",
                    modifier = Modifier.width(width = 200.dp).padding(top = 20.dp),
                    onClick = { viewModel?.goBuildAccount() }
                )
            }
        }
    }

    override fun composeLoaded() {
        viewModel?.requestPermissions()
    }
}
