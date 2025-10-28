package com.module.bostaurus.audio.presentation.mappers

import com.module.bostaurus.audio.presentation.AudioPlayerPresentationState
import com.module.bostaurus.audio.ui.player.model.AudioPlayerUiState

class AudioPlayerPresentationToUiMapper {
    fun mapToUiState(presentationState: AudioPlayerPresentationState): AudioPlayerUiState {
        return AudioPlayerUiState(
            isLoaded = presentationState.isLoaded,
            isPlaying = presentationState.isPlaying,
            currentPosition = presentationState.currentPosition,
            duration = presentationState.duration,
            errorMessage = presentationState.errorMessage.orEmpty()
        )
    }
}
