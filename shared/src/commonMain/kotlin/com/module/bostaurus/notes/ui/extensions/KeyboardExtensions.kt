package com.module.bostaurus.notes.ui.extensions

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.SoftwareKeyboardController

fun FocusRequester.showKeyboard(
    imeVisible: Boolean,
    keyboardController: SoftwareKeyboardController?
) {
    if(imeVisible) {
        keyboardController?.hide()
    } else {
        requestFocus()
        keyboardController?.show()
    }
}
