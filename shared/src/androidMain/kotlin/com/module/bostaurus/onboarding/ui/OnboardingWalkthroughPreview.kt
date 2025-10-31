package com.module.bostaurus.onboarding.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.module.bostaurus.platform.presentation.PlatformUiState

@Preview(
    name = "Onboarding Walkthrough - Phone",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun OnboardingWalkthroughPhonePreview() {
    OnboardingWalkthrough(
        onFinish = {},
        platformState = PlatformUiState(
            isTablet = false,
            isAndroid = true
        )
    )
}

@Preview(
    name = "Onboarding Walkthrough - Tablet",
    showBackground = true,
    showSystemUi = true,
    device = "spec:width=1280dp,height=800dp,dpi=240"
)
@Composable
fun OnboardingWalkthroughTabletPreview() {
    OnboardingWalkthrough(
        onFinish = {},
        platformState = PlatformUiState(
            isTablet = true,
            isAndroid = true
        )
    )
}
