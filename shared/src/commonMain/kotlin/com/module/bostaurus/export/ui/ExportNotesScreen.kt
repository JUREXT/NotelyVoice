package com.module.bostaurus.export.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.module.bostaurus.export.presentation.ExportSelectionViewModel
import com.module.bostaurus.notes.ui.theme.LocalCustomColors
import com.module.bostaurus.platform.getPlatform
import com.module.notelycompose.resources.Res
import com.module.notelycompose.resources.top_bar_back
import com.module.bostaurus.resources.vectors.IcChevronLeft
import com.module.bostaurus.resources.vectors.Images
import org.jetbrains.compose.resources.stringResource

@Composable
fun ExportNotesScreen(
    navigateBack: () -> Unit,
    viewModel: ExportSelectionViewModel
) {
    val exportingState by viewModel.exportingFileState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onExportSelection()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalCustomColors.current.bodyBackgroundColor)
            .windowInsetsPadding(WindowInsets(0))
            .padding(0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(LocalCustomColors.current.bodyBackgroundColor)
        ) {
            Column {
                ComponentBackButton(onNavigateBack = navigateBack)
                ExportingFileStateHost(
                    state = exportingState,
                    onDismiss = {
                        navigateBack()
                        viewModel.releaseState()
                    }
                )
            }
        }
    }
}

@Composable
private fun ComponentBackButton(
    onNavigateBack: () -> Unit
) {
    if (getPlatform().isAndroid) {
        IconButton(
            onClick = {
                onNavigateBack()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(Res.string.top_bar_back),
                tint = LocalCustomColors.current.bodyContentColor
            )
        }
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .clickable {
                    onNavigateBack()
                }
        ) {
            Icon(
                imageVector = Images.Icons.IcChevronLeft,
                contentDescription = stringResource(Res.string.top_bar_back),
                modifier = Modifier.size(28.dp),
                tint = LocalCustomColors.current.bodyContentColor
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(Res.string.top_bar_back),
                style = MaterialTheme.typography.body1,
                color = LocalCustomColors.current.bodyContentColor
            )
        }
    }
}
