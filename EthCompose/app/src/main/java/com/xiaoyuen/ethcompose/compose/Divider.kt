package com.xiaoyuen.ethcompose.compose

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.graphics.Color
import androidx.ui.layout.height
import androidx.ui.material.Divider
import androidx.ui.unit.Dp
import androidx.ui.unit.dp

@Composable
fun DividerTransparent(height: Dp = 10.dp) {
    DividerColor(
        color = Color.Transparent,
        height = height
    )
}

@Composable
fun DividerGray(height: Dp = 10.dp) {
    DividerColor(
        color = Color(0xFFededed),
        height = height
    )
}

@Composable
fun DividerWhite(height: Dp = 10.dp) {
    DividerColor(color = Color.White, height = height)
}

@Composable
fun DividerColor(color: Color = Color.Transparent, height: Dp = 10.dp) {
    Divider(color = color, modifier = Modifier.height(height))
}