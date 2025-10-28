package com.module.bostaurus

import android.app.Application
import com.module.bostaurus.di.initKoinApplication
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class MainApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Napier.base(DebugAntilog())
        initKoinApplication {
            androidContext(this@MainApplication)
            androidLogger()
        }
    }
}