package me.lokmvne.welcome.presentation.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import me.lokmvne.auth.data.model.GoogleUser
import me.lokmvne.auth.repository.GoogleFirebaseAuthRepository
import me.lokmvne.common.utils.Resource
import javax.inject.Inject

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val googleFirebaseAuthRepository: GoogleFirebaseAuthRepository,
) : ViewModel() {
    private val _googleFirebaseLoggedUser = MutableStateFlow(
        GoogleUser(
            "",
            "",
            ""
        )
    )
    val googleFirebaseLoggedUser = _googleFirebaseLoggedUser.asStateFlow()

    private val loginNavigationEvent = Channel<LoginEvent>()
    val loginNavigationEventFlow = loginNavigationEvent.receiveAsFlow()

    private val _currentLoginState = MutableStateFlow(LoginState())
    val currentLoginState = _currentLoginState.asStateFlow()

    fun googleFirebaseSignInVM(context: Context) {
        viewModelScope.launch {
            _currentLoginState.value = _currentLoginState.value.copy(isLoading = true)
            val handleSignInResult =
                googleFirebaseAuthRepository.handleGoogleSignInRepo(context)
            handleSignInResult.let { handleSignInRes ->
                when (handleSignInRes) {
                    is Resource.Success -> {
                        _googleFirebaseLoggedUser.value = handleSignInRes.data!!
                        loginNavigationEvent.send(LoginEvent.NavigateToHomeScreen)
                        _currentLoginState.value =
                            _currentLoginState.value.copy(isLoading = false, isLoggedIn = true)
                    }

                    is Resource.Error -> {
                        _currentLoginState.value = _currentLoginState.value.copy(isLoading = false)
                        Toast.makeText(
                            context,
                            handleSignInResult.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is Resource.Loading -> {}
                }
            }
        }
    }
}

sealed interface LoginEvent {
    data object NavigateToHomeScreen : LoginEvent
}

data class LoginState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
)