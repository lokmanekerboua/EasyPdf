package me.lokmvne.easypdf.presentation.screens.welcomescreens

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.lokmvne.common.utils.Resource
import me.lokmvne.easypdf.data.model.GoogleUser
import me.lokmvne.easypdf.repository.GoogleFirebaseAuthRepository
import javax.inject.Inject

@HiltViewModel
class GoogleFirebaseAuthViewModel @Inject constructor(
    private val welcomeRepository: GoogleFirebaseAuthRepository,
) : ViewModel() {
    private val _googleFirebaseLoggedUser = MutableStateFlow(GoogleUser("", "", ""))
    val googleFirebaseLoggedUser = _googleFirebaseLoggedUser.asStateFlow()

    private val _isGoogleFirebaseSignInSuccessful = MutableStateFlow(false)
    val isGoogleFirebaseSignInSuccessful = _isGoogleFirebaseSignInSuccessful.asStateFlow()

    fun googleFirebaseSignInVM(context: Context) {
        viewModelScope.launch {
            val handleSignInResult =
                welcomeRepository.handleGoogleSignInRepo(context)
            handleSignInResult.let { handleSignInRes ->
                when (handleSignInRes) {
                    is Resource.Success -> {
                        _googleFirebaseLoggedUser.value = handleSignInRes.data!!
                        _isGoogleFirebaseSignInSuccessful.value = true
                    }

                    is Resource.Error -> {
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


    private val _isGoogleFirebaseSignOutSuccessful = MutableStateFlow(false)
    val isGoogleFirebaseSignOutSuccessful = _isGoogleFirebaseSignOutSuccessful.asStateFlow()

    fun googleFirebaseSignOutVM() {
        val logoutProcess = viewModelScope.async {
            welcomeRepository.googleFirebaseSignOutRepo()
        }

        logoutProcess.invokeOnCompletion {
            val result = welcomeRepository.checkIfGoogleFirebaseUserIsSignedInRepo()
            result.let { res ->
                if (!res) {
                    _isGoogleFirebaseSignOutSuccessful.value = true
                }
            }
        }
    }

    fun checkIfGoogleFirebaseUserIsSignedInVM(): Boolean {
        return welcomeRepository.checkIfGoogleFirebaseUserIsSignedInRepo()
    }
}