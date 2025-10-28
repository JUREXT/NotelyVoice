package com.module.bostaurus.notes.domain

import com.module.bostaurus.notes.domain.mapper.NoteDomainMapper
import com.module.bostaurus.notes.domain.model.NoteDomainModel

class GetLastNote(
    private val noteDataSource: NoteDataSource,
    private val noteDomainMapper: NoteDomainMapper
) {
    fun execute(): NoteDomainModel? {
        return noteDataSource.getLastNote()?.let { noteDomainMapper.mapToDomainModel(it) }
    }
}
