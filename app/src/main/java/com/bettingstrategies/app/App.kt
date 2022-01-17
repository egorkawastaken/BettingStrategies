package com.bettingstrategies.app

import android.app.Application
import com.bettingstrategies.di.appModule
import com.bettingstrategies.di.dataModule
import com.bettingstrategies.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    dataModule,
                    domainModule
                )
            )
        }
    }
}