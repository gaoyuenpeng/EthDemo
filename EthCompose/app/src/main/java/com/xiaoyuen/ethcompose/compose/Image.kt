package com.xiaoyuen.ethcompose.compose

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.Image
import androidx.ui.graphics.asImageAsset
import androidx.ui.layout.size
import androidx.ui.unit.Dp
import com.xiaoyuen.ethcompose.scan.QrCodeUtil

@Composable
fun ImageWithQR(
    context: Context,
    qrCode: String,
    width: Dp,
    height: Dp,
    modifier: Modifier = Modifier
) {
    val bitmap = QrCodeUtil.buildQrBitmap(context, qrCode, width)
    Image(bitmap, width, height, modifier)
}

@Composable
fun Image(bitmap: Bitmap?, width: Dp, height: Dp, modifier: Modifier = Modifier) {
    bitmap?.let {
        Image(
            asset = bitmap.asImageAsset(),
            modifier = Modifier.size(width = width, height = height).plus(modifier)
        )
    }
}