package com.xiaoyuen.ethcompose.scan

import com.xiaoyuen.ethcompose.base.Config


data class ScanResultModel(val qrCode: String) {

    var address: String? = null
    var value: String? = null

    init {
        build()
    }

    private fun build() {
        qrCode?.let { qrCode ->

            //address
            if (qrCode.contains(Config.keyAddress)) {
                val start = qrCode.indexOf(Config.keyAddress) + Config.keyAddress.length
                var end = qrCode.length
                if (qrCode.contains("?")) {
                    end = qrCode.indexOf("?")
                }
                address = qrCode.substring(start, end)
            }

            //value
            if (qrCode.contains(Config.keyValue)) {
                val start = qrCode.indexOf(Config.keyValue) + Config.keyValue.length
                var end = qrCode.length
                if (qrCode.indexOf("&", start, false) > 0) {
                    end = qrCode.indexOf("&", start, false)
                }
                value = qrCode.substring(start, end)
            }
        }
    }
}