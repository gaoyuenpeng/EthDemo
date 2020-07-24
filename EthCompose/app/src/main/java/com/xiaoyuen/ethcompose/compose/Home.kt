package com.xiaoyuen.ethcompose.compose

import androidx.compose.Composable
import androidx.compose.MutableState
import androidx.ui.core.Alignment
import androidx.ui.core.ContentScale
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.ColumnScope.weight
import androidx.ui.layout.fillMaxHeight
import androidx.ui.layout.size
import androidx.ui.material.BottomAppBar
import androidx.ui.res.imageResource
import androidx.ui.res.stringResource
import androidx.ui.unit.TextUnit
import androidx.ui.unit.dp
import com.xiaoyuen.ethcompose.entity.HomeTabItems
import com.xiaoyuen.ethcompose.entity.TabItemModel
import com.xiaoyuen.ethcompose.entity.WalletAccount
import com.xiaoyuen.ethcompose.ui.viewmodel.MainViewModel

//bottom bar item
@Composable
fun HomeTabItem(item: TabItemModel, pageState: Int, onClick: () -> Unit) {

    val color = if (item.index == pageState) MainBlue else TextTabNormal
    val imageId = if (item.index == pageState) item.iconSelect else item.iconNormal

    Box(
        modifier = Modifier.fillMaxHeight().weight(1f).clickable(onClick = onClick),
        gravity = ContentGravity.Center
    ) {
        Column(horizontalGravity = Alignment.CenterHorizontally) {
            Image(
                asset = imageResource(id = imageId),
                modifier = Modifier.size(25.dp, 25.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                stringResource(item.name),
                color = color,
                fontSize = TextUnit.Sp(16)
            )
        }
    }
}

//bottom bar
@Composable
fun HomeBottomBar(
    currentPageState: MutableState<Int>,
    fabConfiguration: BottomAppBar.FabConfiguration? = null
) {
    BottomAppBar(
        backgroundColor = Color.White,
        fabConfiguration = fabConfiguration,
        cutoutShape = CircleShape
    ) {
        HomeTabItem(
            HomeTabItems[0],
            currentPageState.value,
            onClick = { currentPageState.value = 0 }
        )
        HomeTabItem(
            HomeTabItems[1],
            currentPageState.value,
            onClick = { currentPageState.value = 1 }
        )
    }
}

//钱包首页
@Composable
fun MainPage(page: Int, walletAccount: WalletAccount?, mainViewModel: MainViewModel?) {
    when (page) {
        0 -> TransactionPage(walletAccount, mainViewModel)
        1 -> WalletPage(walletAccount, mainViewModel)
    }
}