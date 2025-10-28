package com.module.bostaurus.notes.domain

import com.module.bostaurus.core.CommonFlow
import com.module.bostaurus.core.asFlow
import com.module.bostaurus.core.toCommonFlow
import com.module.bostaurus.notes.domain.mapper.NoteDomainMapper
import com.module.bostaurus.notes.domain.model.NoteDomainModel
import kotlinx.coroutines.flow.map

class GetAllNotesUseCase(
    private val noteDataSource: NoteDataSource,
    private val noteDomainMapper: NoteDomainMapper
) {
    fun execute(): CommonFlow<List<NoteDomainModel>> {
        return noteDataSource.getNotes().asFlow()
        .map { notes ->
            notes.map { noteDataModel ->
                noteDomainMapper.mapToDomainModel(noteDataModel)
            }
        }.toCommonFlow()
    }
}
