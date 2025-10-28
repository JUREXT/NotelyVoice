package com.module.bostaurus.export.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.module.bostaurus.export.domain.ExportSelectionInteractor
import com.module.bostaurus.export.domain.NoFolderSelectedException
import com.module.bostaurus.export.presentation.model.ExportSelectionPresentationState
import com.module.bostaurus.export.presentation.model.ExportingFileState
import com.module.bostaurus.notes.domain.GetAllNotesUseCase
import com.module.bostaurus.notes.presentation.mapper.NotePresentationMapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class ExportSelectionViewModel(
    private val getAllNotesUseCase: GetAllNotesUseCase,
    private val notePresentationMapper: NotePresentationMapper,
    private val exportSelectionInteractor: ExportSelectionInteractor
) : ViewModel() {

    private val _state = MutableStateFlow(ExportSelectionPresentationState())
    val state: StateFlow<ExportSelectionPresentationState> = _state

    private var _exportingState = MutableStateFlow<ExportingFileState>(ExportingFileState.Idle)
    val exportingFileState: StateFlow<ExportingFileState> = _exportingState

    fun onUpdateNoteIds(
        noteIds: List<Long>
    ) {
        _state.update { currentState ->
            currentState.copy(
                noteIds = noteIds
            )
        }
    }

    fun onUpdateExportOptions(
        shouldExportAudio: Boolean,
        shouldExportTxt: Boolean
    ) {
        _state.update { currentState ->
            currentState.copy(
                shouldExportAudio = shouldExportAudio,
                shouldExportTxt = shouldExportTxt
            )
        }
        onFilterSelectedNotes()
    }

    private fun onFilterSelectedNotes() {
        combine(
            getAllNotesUseCase.execute(),
            _state.map { it }.distinctUntilChanged(),
        ) { notes, noteIds ->
            Pair(notes, noteIds)
        }.onEach { (notes, ids) ->
            val filteredNotes = notes.filter { note ->
                ids.noteIds.contains(note.id)
            }
            val selectedNotes = filteredNotes.map { notePresentationMapper.mapToPresentationModel(it) }
            _state.update { currentState ->
                currentState.copy(
                    selectedNotes = selectedNotes
                )
            }
        }.launchIn(viewModelScope)
    }

    fun onExportSelection() {
        val texts = _state.value.selectedNotes.map { it.content }
        val titles =  _state.value.selectedNotes.map { it.title }
        val audioPath =  _state.value.selectedNotes.map { it.recordingPath }
        viewModelScope.launch {
            delay(2.seconds)
            _exportingState.update { ExportingFileState.Exporting() }
        }
        exportSelectionInteractor.exportAllSelection(
            texts = texts,
            titles = titles,
            audioPath = audioPath,
            shouldExportAudio = _state.value.shouldExportAudio,
            shouldExportTxt = _state.value.shouldExportTxt,
            onProgress = { progress ->
                _exportingState.update { ExportingFileState.Exporting(progress) }
            }
        ) { result ->
            _exportingState.update {
                when {
                    result.isSuccess -> ExportingFileState.Success
                    else -> {
                        if(result.exceptionOrNull() is NoFolderSelectedException) {
                            ExportingFileState.NoFolderSelected
                        } else {
                            ExportingFileState.Failure(
                                result.exceptionOrNull()?.message ?: "Export failed"
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onCleared() {
        _state.value = ExportSelectionPresentationState()
        super.onCleared()
    }

    internal fun releaseState() = viewModelScope.launch {
        _exportingState.update { ExportingFileState.Idle }
    }
}
