package com.module.bostaurus

import androidx.compose.ui.window.ComposeUIViewController
import com.module.bostaurus.di.init
import org.koin.compose.KoinApplication

fun mainViewController() = ComposeUIViewController {
    KoinApplication(application = {
        init()
    }) {
        Main()
    }
}