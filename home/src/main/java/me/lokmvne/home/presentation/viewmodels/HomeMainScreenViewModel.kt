package me.lokmvne.home.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import me.lokmvne.auth.repository.GoogleFirebaseAuthRepository
import javax.inject.Inject

@HiltViewModel
class HomeMainScreenViewModel @Inject constructor(
    private val googleFirebaseAuthRepository: GoogleFirebaseAuthRepository
) : ViewModel() {

    private val _signOutEventChannel = Channel<SignOutState>()
    val signOutEventChannelFlow = _signOutEventChannel.receiveAsFlow()

    fun googleFirebaseSignOutVM() {
        viewModelScope.launch {
            googleFirebaseAuthRepository.googleFirebaseSignOutRepo()
            _signOutEventChannel.send(SignOutState.SignOutSuccess)
        }
    }
}

sealed interface SignOutState {
    data object SignOutSuccess : SignOutState
    data object SignOutFailure : SignOutState
}