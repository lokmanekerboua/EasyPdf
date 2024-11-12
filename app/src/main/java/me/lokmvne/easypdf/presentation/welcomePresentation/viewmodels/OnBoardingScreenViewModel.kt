package me.lokmvne.easypdf.presentation.welcomePresentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.lokmvne.common.repository.DataPreferencesRepository
import javax.inject.Inject

@HiltViewModel
class OnBoardingScreenViewModel @Inject constructor(
    private val dataPreferencesRepository: DataPreferencesRepository
) : ViewModel() {
    private val _onboardingCompleted = MutableStateFlow(true)
    val onboardingCompleted = _onboardingCompleted.asStateFlow()

    fun setOnboardingCompleted() {
        viewModelScope.launch {
            dataPreferencesRepository.saveOnboardingComplete()
            _onboardingCompleted.value = true
        }
    }
}