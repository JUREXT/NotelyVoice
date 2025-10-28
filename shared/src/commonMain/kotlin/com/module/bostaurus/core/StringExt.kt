package com.module.bostaurus.core

fun String.sanitiseText(): String {
    return this.replace(Regex("[\\r\\n]+"), "")
}

fun String.keepFirstCharCaseExt(): String =
    if (isEmpty()) "" else first() + substring(1).lowercase()
