package com.module.bostaurus.notes.presentation.detail.model

data class TextPresentationFormat(
    val range: IntRange,
    val isBold: Boolean = false,
    val isItalic: Boolean = false,
    val isUnderline: Boolean = false,
    val textSize: Float? = null
)
