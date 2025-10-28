package com.module.bostaurus.notes.presentation.list

import com.module.bostaurus.notes.presentation.list.mapper.NotesFilterConstants
import com.module.bostaurus.notes.presentation.list.model.NotePresentationModel

data class NoteListPresentationState(
    val originalNotes: List<NotePresentationModel> = emptyList(),
    val filteredNotes: List<NotePresentationModel> = emptyList(),
    val selectedTabIndex: Int = NotesFilterConstants.ALL,
    val showEmptyContent: Boolean = false,
    val allNotesSizeStr: String = ""
)
