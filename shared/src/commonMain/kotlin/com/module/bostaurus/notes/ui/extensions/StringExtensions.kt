package com.module.bostaurus.notes.ui.extensions

fun String.firstToUpperCase(): String {
    return this.replaceFirstChar { it.uppercaseChar() }
}
