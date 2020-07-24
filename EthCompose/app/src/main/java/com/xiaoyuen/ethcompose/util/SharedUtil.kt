package com.xiaoyuen.ethcompose.util

import android.content.Context
import android.content.SharedPreferences


object SharedUtil {

    private const val fileName = "shared_data"

    fun putString(context: Context, key: String, value: String?) {
        val editor = getSP(context).edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun getString(context: Context, key: String, default: String = ""): String? {
        return getSP(context).getString(key, default)
    }

    fun remove(context: Context, key: String) {
        val editor = getSP(context).edit()
        editor.remove(key)
        editor.commit()
    }

    private fun getSP(context: Context): SharedPreferences =
        context.getSharedPreferences(fileName, Context.MODE_PRIVATE)

}