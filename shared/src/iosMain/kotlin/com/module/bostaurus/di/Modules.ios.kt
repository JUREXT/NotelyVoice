package com.module.bostaurus.di
import com.module.bostaurus.audio.domain.AudioRecorderInteractor
import com.module.bostaurus.audio.domain.AudioRecorderInteractorImpl
import com.module.notelycompose.database.NoteDatabase
import com.module.bostaurus.export.domain.ExportSelectionInteractor
import com.module.bostaurus.export.domain.ExportSelectionInteractorImpl
import com.module.bostaurus.platform.BrowserLauncher
import com.module.bostaurus.platform.Downloader
import com.module.bostaurus.platform.IOSPlatform
import com.module.bostaurus.platform.Platform
import com.module.bostaurus.platform.PlatformAudioPlayer
import com.module.bostaurus.platform.PlatformUtils
import com.module.bostaurus.platform.Transcriber
import com.module.bostaurus.platform.dataStore
import com.module.bostaurus.platform.pdf.IOSPdfGenerator
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import org.koin.core.qualifier.named
import org.koin.dsl.module
import platform.Foundation.NSBundle

actual val platformModule = module {

    single<Platform> { IOSPlatform() }
    single { PlatformUtils(get()) }
    single { BrowserLauncher() }
    single { dataStore() }
    single { IOSPdfGenerator() }

    single<SqlDriver> {
        NativeSqliteDriver(NoteDatabase.Schema, "notes.db")
    }

    single<String>(qualifier = named("AppVersion")) {
        NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleShortVersionString") as? String
            ?: "Unknown"
    }


    single { PlatformAudioPlayer() }

    single { Downloader() }

    single { Transcriber() }

    // domain
    single<AudioRecorderInteractor> { AudioRecorderInteractorImpl(get(), get()) }
    single<ExportSelectionInteractor> { ExportSelectionInteractorImpl() }
}