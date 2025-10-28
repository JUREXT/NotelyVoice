package com.module.bostaurus.transcription

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.module.bostaurus.core.debugPrintln
import com.module.bostaurus.modelDownloader.ModelSelection
import com.module.bostaurus.onboarding.data.PreferencesRepository
import com.module.bostaurus.platform.Transcriber
import com.module.bostaurus.summary.Text2Summary
import com.module.bostaurus.transcription.textAnalysis.getSegmenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val SPACE_STR = " "

class TranscriptionViewModel(
    private val transcriber: Transcriber,
    private val preferencesRepository: PreferencesRepository,
    private val modelSelection: ModelSelection
) :ViewModel(){
    private val _uiState = MutableStateFlow(TranscriptionUiState())
    val uiState: StateFlow<TranscriptionUiState> = _uiState

    fun requestAudioPermission() {
        viewModelScope.launch {
            transcriber.requestRecordingPermission()
        }
    }

    fun initRecognizer() {
        viewModelScope.launch(Dispatchers.IO) {
            val modelFileName = modelSelection.getSelectedModel()
            transcriber.initialize(modelFileName.name)
        }
    }


    fun startRecognizer(filePath: String) {
        debugPrintln{"startRecognizer ========================="}
        viewModelScope.launch(Dispatchers.Default) {
            if (transcriber.hasRecordingPermission()) {
                _uiState.update { current ->
                    current.copy(inTranscription = true)
                }
                val transcriptionLanguage = preferencesRepository.getDefaultTranscriptionLanguage().first()
                val segmenter = getSegmenter(transcriptionLanguage)
                transcriber.start(
                    filePath, preferencesRepository.getDefaultTranscriptionLanguage().first(), onProgress = { progress ->
                        debugPrintln{"progress ========================= $progress"}
                        _uiState.update { current ->
                            current.copy(
                                progress = progress
                            )
                        }
                    }, onNewSegment = { _, _, text ->
                        
                        val delimiter = if(_uiState.value.originalText.endsWith(".")) "\n\n" else SPACE_STR
                        debugPrintln{"\n text ========================= $text"}
                        _uiState.update { current ->
                            // TODO: Verify this change
                            current.copy(
                                // originalText = "${_uiState.value.originalText}$delimiter${text.trim()}".trim(),
                                originalText = segmenter.segmentText("${_uiState.value.originalText.trim()}$delimiter${text.trim()}".trim()).joinToString("\n\n"),
                                partialText = text
                            )
                        }

                    },
                    onComplete = {
                        debugPrintln{"\n completed ========================= "}
                        _uiState.update {current ->
                            current.copy(
                                inTranscription = false
                            )
                        }
                    },
                    onError = {
                        debugPrintln{"\n error ========================= "}
                        _uiState.update {current ->
                            current.copy(
                                inTranscription = false,
                                progress = 100,
                                hasError = true
                            )
                        }
                    })
            }
        }

    }

    fun stopRecognizer() {
        _uiState.update { current ->
            current.copy(inTranscription = false)
        }
        viewModelScope.launch {
            transcriber.stop()
        }

    }

    fun finishRecognizer() {
        _uiState.update { current ->
            current.copy(
                inTranscription = false,
                originalText = "",
                finalText = "",
                partialText = "",
                summarizedText = ""
            )
        }
        viewModelScope.launch {
            transcriber.finish()
        }
    }

    fun summarize() {
        if (_uiState.value.viewOriginalText) {
            viewModelScope.launch {
                val summarizedText = Text2Summary.summarize(_uiState.value.originalText, 0.7f)
                _uiState.update { current ->
                    current.copy(viewOriginalText = false, summarizedText = summarizedText)
                }

            }
        } else {
            _uiState.update { current ->
                current.copy(viewOriginalText = true)
            }
        }

    }

    override fun onCleared() {
        stopRecognizer()
    }
}
