package com.module.bostaurus.notes.presentation.model

import com.module.bostaurus.notes.domain.model.TextAlignDomainModel
import com.module.bostaurus.notes.domain.model.TextFormatDomainModel
import kotlinx.datetime.LocalDateTime

data class NotePresentationModel(
    val id: Long,
    val title: String,
    val content: String,
    val starred: Long,
    val formatting: List<TextFormatDomainModel>,
    val textAlign: TextAlignDomainModel,
    val createdAt: LocalDateTime
)
