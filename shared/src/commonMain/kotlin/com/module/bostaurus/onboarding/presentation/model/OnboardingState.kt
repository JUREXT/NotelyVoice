package com.module.bostaurus.onboarding.presentation.model

sealed class OnboardingState {
    object Initial : OnboardingState()
    object NotCompleted : OnboardingState()
    object Completed : OnboardingState()
}
