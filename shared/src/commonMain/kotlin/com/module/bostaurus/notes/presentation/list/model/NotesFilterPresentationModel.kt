package com.module.bostaurus.notes.presentation.list.model

sealed class NotesFilterPresentationModel {
    object ALL : NotesFilterPresentationModel()
    object STARRED : NotesFilterPresentationModel()
    object VOICES : NotesFilterPresentationModel()
    object RECENT : NotesFilterPresentationModel()
}
