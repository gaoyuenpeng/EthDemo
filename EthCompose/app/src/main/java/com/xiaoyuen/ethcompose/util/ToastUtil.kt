package com.xiaoyuen.ethcompose.util


import android.content.Context
import android.widget.Toast

object ToastUtil {

    fun show(context: Context?, message: String?) {
        if (context == null || message.isNullOrEmpty()) {
            return
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}