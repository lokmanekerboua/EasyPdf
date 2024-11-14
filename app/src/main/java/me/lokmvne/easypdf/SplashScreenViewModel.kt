package me.lokmvne.easypdf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import me.lokmvne.auth.repository.GoogleFirebaseAuthRepository
import me.lokmvne.common.repository.DataPreferencesRepository
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val dataPreferencesRepository: DataPreferencesRepository,
    private val googleFirebaseAuthRepository: GoogleFirebaseAuthRepository
) : ViewModel() {

    private val _startDestinationChannel = Channel<StartDestination>()
    val startDestinationChannelFlow = _startDestinationChannel.receiveAsFlow()

    fun readOnboardingCompleted() {
        viewModelScope.launch {
            dataPreferencesRepository.readOnboardingComplete().collectLatest {
                if (!it) {
                    _startDestinationChannel.send(StartDestination.WelcomeOnBoardingScreen)
                } else {
                    googleFirebaseAuthRepository.checkIfGoogleFirebaseUserIsSignedInRepo()
                        .let { isSignedIn ->
                            if (isSignedIn) {
                                _startDestinationChannel.send(StartDestination.HomeScreen)
                            } else {
                                _startDestinationChannel.send(StartDestination.WelcomeLoginScreen)
                            }
                        }
                }
            }
        }
    }
}

sealed interface StartDestination {
    data object WelcomeLoginScreen : StartDestination
    data object WelcomeOnBoardingScreen : StartDestination
    data object HomeScreen : StartDestination
}