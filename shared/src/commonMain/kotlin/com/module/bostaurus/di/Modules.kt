package com.module.bostaurus.di

import com.module.bostaurus.audio.presentation.AudioPlayerViewModel
import com.module.bostaurus.audio.presentation.AudioRecorderViewModel
import com.module.bostaurus.audio.presentation.mappers.AudioPlayerPresentationToUiMapper
import com.module.bostaurus.audio.presentation.mappers.AudioRecorderPresentationToUiMapper
import com.module.notelycompose.database.NoteDatabase
import com.module.bostaurus.modelDownloader.ModelDownloaderViewModel
import com.module.bostaurus.notes.data.NoteSqlDelightDataSource
import com.module.bostaurus.notes.domain.DeleteNoteById
import com.module.bostaurus.notes.domain.GetAllNotesUseCase
import com.module.bostaurus.notes.domain.GetLastNote
import com.module.bostaurus.notes.domain.GetNoteById
import com.module.bostaurus.notes.domain.InsertNoteUseCase
import com.module.bostaurus.notes.domain.NoteDataSource
import com.module.bostaurus.notes.domain.SearchNotesUseCase
import com.module.bostaurus.notes.domain.UpdateNoteUseCase
import com.module.bostaurus.notes.domain.mapper.NoteDomainMapper
import com.module.bostaurus.notes.domain.mapper.TextFormatMapper
import com.module.bostaurus.audio.presentation.AudioImportViewModel
import com.module.bostaurus.export.presentation.ExportSelectionViewModel
import com.module.bostaurus.modelDownloader.ModelSelection
import com.module.bostaurus.notes.presentation.detail.NoteDetailScreenViewModel
import com.module.bostaurus.notes.presentation.detail.TextEditorViewModel
import com.module.bostaurus.notes.presentation.helpers.TextEditorHelper
import com.module.bostaurus.notes.presentation.list.NoteListViewModel
import com.module.bostaurus.notes.presentation.list.mapper.NotesFilterMapper
import com.module.bostaurus.notes.presentation.mapper.EditorPresentationToUiStateMapper
import com.module.bostaurus.notes.presentation.mapper.NotePresentationMapper
import com.module.bostaurus.notes.presentation.mapper.TextAlignPresentationMapper
import com.module.bostaurus.notes.presentation.mapper.TextFormatPresentationMapper
import com.module.bostaurus.onboarding.data.PreferencesRepository
import com.module.bostaurus.onboarding.presentation.OnboardingViewModel
import com.module.bostaurus.platform.presentation.PlatformViewModel
import com.module.bostaurus.transcription.TranscriptionViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


internal expect val platformModule: Module

val appModule = module {

    single<NoteDataSource> {
        NoteSqlDelightDataSource(
            database = NoteDatabase(get())
        )
    }

    factory { ModelSelection(get()) }

}

val mapperModule = module {
    single { EditorPresentationToUiStateMapper() }
    single { AudioPlayerPresentationToUiMapper() }
    single { AudioRecorderPresentationToUiMapper() }
    single { NoteDomainMapper(get()) }
    single { TextFormatMapper() }
    single { NotesFilterMapper() }
    single { NotePresentationMapper() }
    single { TextFormatPresentationMapper() }
    single { TextAlignPresentationMapper() }
    single { TextEditorHelper() }
}
val repositoryModule = module {
    singleOf(::PreferencesRepository)
}

val viewModelModule = module {
    viewModelOf(::OnboardingViewModel)
    viewModelOf(::NoteListViewModel)
    viewModelOf(::PlatformViewModel)
    viewModelOf(::TranscriptionViewModel)
    viewModelOf(::TextEditorViewModel)
    viewModelOf(::NoteDetailScreenViewModel)
    viewModelOf(::ModelDownloaderViewModel)
    viewModelOf(::AudioRecorderViewModel)
    viewModelOf(::AudioPlayerViewModel)
    viewModelOf(::AudioImportViewModel)
    viewModelOf(::ExportSelectionViewModel)
}

val useCaseModule = module {
    factory { DeleteNoteById(get()) }
    factory { GetAllNotesUseCase(get(), get()) }
    factory { GetLastNote(get(), get()) }
    factory { GetNoteById(get(), get()) }
    factory { InsertNoteUseCase(get(), get(), get()) }
    factory { SearchNotesUseCase(get(), get()) }
    factory { UpdateNoteUseCase(get(), get(), get()) }
}
