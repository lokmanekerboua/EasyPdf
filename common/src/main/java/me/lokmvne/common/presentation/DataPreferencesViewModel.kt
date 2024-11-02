package me.lokmvne.common.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.lokmvne.common.repository.DataPreferencesRepository
import javax.inject.Inject

@HiltViewModel
class DataPreferencesViewModel @Inject constructor(
    private val dataPreferencesRepository: DataPreferencesRepository
) : ViewModel() {
    private val _onboardingCompleted = MutableStateFlow(true)
    val onboardingCompleted = _onboardingCompleted.asStateFlow()

    fun setOnboardingCompleted() {
        val saveOnboardingCompleteJob = viewModelScope.async {
            dataPreferencesRepository.saveOnboardingComplete()
            //_onboardingCompleted.value = true
        }
        saveOnboardingCompleteJob.invokeOnCompletion {
            readOnboardingCompleted()
        }
    }

    fun readOnboardingCompleted() {
        viewModelScope.launch {
            dataPreferencesRepository.readOnboardingComplete().collectLatest {
                _onboardingCompleted.value = it
            }
        }
    }
}