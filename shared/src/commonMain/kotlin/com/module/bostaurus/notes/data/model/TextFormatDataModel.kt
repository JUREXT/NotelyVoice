package com.module.bostaurus.notes.data.model

import com.module.bostaurus.notes.domain.serializer.IntRangeSerializer
import kotlinx.serialization.Serializable

@Serializable
data class TextFormatDataModel(
    @Serializable(with = IntRangeSerializer::class)
    val range: IntRange,
    val isBold: Boolean = false,
    val isItalic: Boolean = false,
    val isUnderline: Boolean = false,
    val textSize: Float? = null
)
