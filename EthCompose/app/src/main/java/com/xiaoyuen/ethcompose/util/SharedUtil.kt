package com.xiaoyuen.ethcompose.util

import android.content.Context


object SharedUtil {

    private const val fileName = "shared_data"

    fun putString(context: Context, key: String, value: String?) {
        val sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun getString(context: Context, key: String, default: String = ""): String? {
        val sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, default)
    }

    fun remove(context: Context, key: String) {
        val sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove(key)
        editor.commit()
    }

}