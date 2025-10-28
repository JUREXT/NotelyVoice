package com.module.bostaurus.platform

import androidx.compose.runtime.Composable

@Composable
expect fun HandlePlatformBackNavigation(enabled: Boolean, onBack: () -> Unit)