package com.xiaoyuen.ethcompose.util


import android.util.Log

object LogUtil {
    private const val tag = "gyp"

    private const val lengthMax = 3 * 1024

    fun e(msg: String?) {
        e(tag, msg)
    }

    fun e(tag: String, msg: String?) {
        if (msg == null || msg.isEmpty()) {
            log(tag, "")
        }
        if (msg!!.length > lengthMax) {
            var i = 0
            while (i < msg.length) {
                if (i + lengthMax < msg.length) {
                    log(
                        tag,
                        msg.substring(
                            i,
                            i + lengthMax
                        )
                    )
                } else {
                    log(
                        tag,
                        msg.substring(i)
                    )
                }
                i += lengthMax
            }
        } else {
            log(tag, msg)
        }
    }

    private fun log(tag: String, msg: String) {
        Log.e(tag, msg)
    }
}