package com.xiaoyuen.ethcompose.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

object ViewModelFactory {

    //splash
    fun buildForSplash(context: Context): SplashViewModel {
        return ViewModelProvider(context as ViewModelStoreOwner,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return SplashViewModel(context) as T
                }
            }).get(SplashViewModel::class.java)
    }

    //创建账号
    fun buildForAccountBuild(context: Context): AccountBuildViewModel {
        return ViewModelProvider(context as ViewModelStoreOwner,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return AccountBuildViewModel(context) as T
                }
            }).get(AccountBuildViewModel::class.java)
    }

    //创建账号助记词
    fun buildForAccountBuildMnemonics(context: Context): AccountBuildMnemonicsViewModel {
        return ViewModelProvider(context as ViewModelStoreOwner,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return AccountBuildMnemonicsViewModel(context) as T
                }
            }).get(AccountBuildMnemonicsViewModel::class.java)
    }

    //转账
    fun buildForTransfer(context: Context): TransferViewModel {
        return ViewModelProvider(context as ViewModelStoreOwner,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return TransferViewModel(context) as T
                }
            }).get(TransferViewModel::class.java)
    }

    //登录
    fun buildForLogin(context: Context): AccountLoginViewModel {
        return ViewModelProvider(context as ViewModelStoreOwner,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return AccountLoginViewModel(context) as T
                }
            }).get(AccountLoginViewModel::class.java)
    }

    //首页
    fun buildForMain(context: Context): MainViewModel {
        return ViewModelProvider(context as ViewModelStoreOwner,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return MainViewModel(context) as T
                }
            }).get(MainViewModel::class.java)
    }

    //收款
    fun buildForCollection(context: Context): CollectionViewModel {
        return ViewModelProvider(context as ViewModelStoreOwner,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                    return CollectionViewModel(context) as T
                }
            }).get(CollectionViewModel::class.java)
    }

}