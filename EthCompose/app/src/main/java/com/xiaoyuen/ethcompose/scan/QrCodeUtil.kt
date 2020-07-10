package com.xiaoyuen.ethcompose.scan

import android.content.Context
import android.graphics.Bitmap
import androidx.ui.unit.Dp
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder
import com.xiaoyuen.ethcompose.base.Config

object QrCodeUtil {

    fun buildColletionString(address: String?, amount: String?): String? {

        val sb = StringBuffer()

        if (address != null && address.isNotEmpty()) {
            sb.append(Config.keyAddress).append(address)
        }

        if (amount != null && amount.isNotEmpty()) {
            sb.append("?").append(Config.keyValue).append(amount)
        }

        return sb.toString()
    }

    fun buildQrBitmap(context: Context, qrCode: String, size: Dp): Bitmap {
        val sizeInt = BGAQRCodeUtil.dp2px(context, size.value)
        return buildQrBitmap(qrCode, sizeInt)
    }

    fun buildQrBitmap(qrCode: String, size: Int): Bitmap {
        return QRCodeEncoder.syncEncodeQRCode(qrCode, size)
    }
}