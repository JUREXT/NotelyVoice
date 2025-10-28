package com.module.bostaurus.di

import android.app.Application
import com.module.bostaurus.FileSaverHandler
import com.module.bostaurus.FileSaverLauncherHolder
import com.module.bostaurus.FolderPickerHandler
import com.module.bostaurus.FolderPickerLauncherHolder
import com.module.bostaurus.audio.domain.AudioRecorderInteractor
import com.module.bostaurus.audio.domain.AudioRecorderInteractorImpl
import com.module.bostaurus.audio.domain.SaveAudioNoteInteractor
import com.module.bostaurus.audio.domain.SaveAudioNoteInteractorImpl
import com.module.notelycompose.database.NoteDatabase
import com.module.bostaurus.export.domain.ExportSelectionInteractor
import com.module.bostaurus.export.domain.ExportSelectionInteractorImpl
import com.module.bostaurus.platform.AndroidPlatform
import com.module.bostaurus.platform.BrowserLauncher
import com.module.bostaurus.platform.Downloader
import com.module.bostaurus.platform.Platform
import com.module.bostaurus.platform.PlatformAudioPlayer
import com.module.bostaurus.platform.PlatformUtils
import com.module.bostaurus.platform.Transcriber
import com.module.bostaurus.platform.dataStore
import com.module.bostaurus.platform.pdf.AndroidPdfGenerator
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual val platformModule = module {
    single<String>(qualifier = named("AppVersion")) {
        val app: Application = get()
        try {
            val packageInfo = app.packageManager.getPackageInfo(app.packageName, 0)
            packageInfo.versionName ?: "Unknown"
        } catch (e: Exception) {
            "Unknown"
        }
    }
    single { FileSaverLauncherHolder() }
    single { FileSaverHandler(get()) }
    single<Platform> { AndroidPlatform(get(named("AppVersion")), get()) }
    single { dataStore(get()) }
    single { PlatformUtils(get(), get(), get()) }
    single { BrowserLauncher(get()) }
    single { AndroidPdfGenerator(get()) }

    single<SqlDriver> {
        AndroidSqliteDriver(NoteDatabase.Schema, context = get(), "notes.db")
    }

    single { PlatformAudioPlayer() }

    single { Downloader(get(), get()) }

    single { Transcriber(get(), get()) }


    // domain
    single<AudioRecorderInteractor> { AudioRecorderInteractorImpl(get(), get(), get()) }
    single<SaveAudioNoteInteractor> {
        SaveAudioNoteInteractorImpl(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

    // export
    single { FolderPickerLauncherHolder() }
    single { FolderPickerHandler(get()) }
    single<ExportSelectionInteractor> {
        ExportSelectionInteractorImpl(
            context = get(),
            folderPickerHandler = get()
        )
    }
}
