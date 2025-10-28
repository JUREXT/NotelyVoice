package com.programming.nativelogger

import android.util.Log

/**
 * NativeLogger - A lightweight logging utility that wraps Android's Log methods.
 * Supports global toggling and tag overrides.
 *
 * Usage:
 *     d { "User logged in: $userId" }                   // uses global tag
 *     d(e) { "Login failed for $userId" }               // with Throwable
 *     d("CustomTag") { "Temporary custom tag log" }     // one-off tag
 *     NativeLogger.setTag("MyApp")                      // set global tag
 *     NativeLogger.initLogger(isLoggingEnabled = true) *
 */
object NativeLogger {

    private const val DEFAULT_TAG = "WHAT"

    var isLoggingEnabled: Boolean = true
        private set

    private var globalTag: String = DEFAULT_TAG

    fun initLogger(isLoggingEnabled: Boolean) {
        this.isLoggingEnabled = isLoggingEnabled
    }

    fun setTag(tag: String) {
        this.globalTag = tag
    }

    fun resolveTag(tag: String?): String = tag ?: globalTag

    // DEBUG
    inline fun d(tag: String? = null, message: () -> String) {
        if (isLoggingEnabled) Log.d(resolveTag(tag), message())
    }

    inline fun d(error: Throwable, tag: String? = null, message: () -> String) {
        if (isLoggingEnabled) Log.d(resolveTag(tag), message(), error)
    }

    // INFO
    inline fun i(tag: String? = null, message: () -> String) {
        if (isLoggingEnabled) Log.i(resolveTag(tag), message())
    }

    inline fun i(error: Throwable, tag: String? = null, message: () -> String) {
        if (isLoggingEnabled) Log.i(resolveTag(tag), message(), error)
    }

    // WARNING
    inline fun w(tag: String? = null, message: () -> String) {
        if (isLoggingEnabled) Log.w(resolveTag(tag), message())
    }

    inline fun w(error: Throwable, tag: String? = null, message: () -> String) {
        if (isLoggingEnabled) Log.w(resolveTag(tag), message(), error)
    }

    // ERROR
    inline fun e(tag: String? = null, message: () -> String) {
        if (isLoggingEnabled) Log.e(resolveTag(tag), message())
    }

    inline fun e(error: Throwable, tag: String? = null, message: () -> String) {
        if (isLoggingEnabled) Log.e(resolveTag(tag), message(), error)
    }
}