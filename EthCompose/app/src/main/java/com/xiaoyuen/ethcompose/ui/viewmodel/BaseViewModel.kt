package com.xiaoyuen.ethcompose.ui.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.xiaoyuen.ethcompose.ui.view.*

open class BaseViewModel(val context: Context) : ViewModel() {

    protected val loadingData = MutableLiveData<Boolean>()//loading对话框
    protected val toastMsgData = MutableLiveData<String>()//toast文案

    fun loadingData(): MutableLiveData<Boolean> {
        return loadingData
    }

    fun toastMsgData(): MutableLiveData<String> {
        return toastMsgData
    }

    fun goMain() {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    fun goIncome() {
        val intent = Intent(context, CollectionActivity::class.java)
        context.startActivity(intent)
    }

    fun goTransfer() {
        val intent = Intent(context, TransferActivity::class.java)
        context.startActivity(intent)
    }

    fun goBuildAccount() {
        val intent = Intent(context, AccountBuildActivity::class.java)
        context.startActivity(intent)
    }

    fun goLiginAccount() {
        val intent = Intent(context, AccountLoginActivity::class.java)
        context.startActivity(intent)
    }

    fun goMnemonics() {
        val intent = Intent(context, AccountBuildMnemonicsActivity::class.java)
        context.startActivity(intent)
    }

    fun goSplash() {
        val intent = Intent(context, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }
}