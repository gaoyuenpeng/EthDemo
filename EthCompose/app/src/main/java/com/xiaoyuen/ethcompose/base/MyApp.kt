package com.xiaoyuen.ethcompose.base

import android.app.Application

class MyApp : Application() {

    private var instance: MyApp? = null

    override fun onCreate() {
        super.onCreate()
        this.instance = this
    }

    fun getInstance(): MyApp? {
        return instance
    }
}