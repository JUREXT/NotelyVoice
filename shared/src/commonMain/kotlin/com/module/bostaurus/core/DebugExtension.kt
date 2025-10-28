package com.module.bostaurus.core

import com.module.bostaurus.platform.isDebugMode

// TODO: Use native debug module
inline fun debugPrintln(message: () -> Any?) {
    // Turn flag to true to test on iOS before proper implementation
    if (isDebugMode()) {
        println(message())
    }
}
