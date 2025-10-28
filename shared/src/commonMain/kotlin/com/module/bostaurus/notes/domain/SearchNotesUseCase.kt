package com.module.bostaurus.notes.domain

import com.module.bostaurus.core.CommonFlow
import com.module.bostaurus.core.asFlow
import com.module.bostaurus.core.toCommonFlow
import com.module.bostaurus.notes.domain.mapper.NoteDomainMapper
import com.module.bostaurus.notes.domain.model.NoteDomainModel
import kotlinx.coroutines.flow.map

class SearchNotesUseCase(
    private val noteDataSource: NoteDataSource,
    private val noteDomainMapper: NoteDomainMapper
) {
    fun execute(keyword: String): CommonFlow<List<NoteDomainModel>> {
        return noteDataSource.getNotesByKeyword(keyword).asFlow().map { notes ->
            notes.map { noteDataModel ->
                noteDomainMapper.mapToDomainModel(noteDataModel)
            }
        }.toCommonFlow()
    }
}
