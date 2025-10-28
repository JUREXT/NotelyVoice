package com.module.bostaurus.audio.presentation.mappers

import com.module.bostaurus.audio.domain.AudioRecorderPresentationState
import com.module.bostaurus.audio.ui.recorder.AudioRecorderUiState

class AudioRecorderPresentationToUiMapper {
    fun mapToUiState(presentationState: AudioRecorderPresentationState): AudioRecorderUiState {
        return AudioRecorderUiState(
            recordCounterString = presentationState.recordCounterString,
            recordingPath = presentationState.recordingPath,
            isRecordPaused = presentationState.isRecordPaused
        )
    }
}
