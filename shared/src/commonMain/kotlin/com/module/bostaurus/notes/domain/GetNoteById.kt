package com.module.bostaurus.notes.domain

import com.module.bostaurus.notes.domain.mapper.NoteDomainMapper
import com.module.bostaurus.notes.domain.model.NoteDomainModel

class GetNoteById(
    private val noteDataSource: NoteDataSource,
    private val noteDomainMapper: NoteDomainMapper
) {
    fun execute(id: Long): NoteDomainModel? {
        return noteDataSource.getNoteById(id)?.let { noteDomainMapper.mapToDomainModel(it) }
    }
}
