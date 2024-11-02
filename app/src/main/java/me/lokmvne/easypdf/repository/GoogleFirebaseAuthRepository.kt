package me.lokmvne.easypdf.repository

import android.content.Context
import me.lokmvne.common.data.DataPreferences
import me.lokmvne.common.utils.Resource
import me.lokmvne.easypdf.data.GoogleAuthUiClient
import me.lokmvne.easypdf.data.model.GoogleUser
import javax.inject.Inject

class GoogleFirebaseAuthRepository @Inject constructor(
    private val googleAuthUiClient: GoogleAuthUiClient,
    private val dataPreferences: DataPreferences,
) {
    suspend fun handleGoogleSignInRepo(
        // credentialManager: CredentialManager,
        context: Context
    ): Resource<GoogleUser> {
        val googleSignInResult = googleAuthUiClient.googleSignIn(context)
        googleSignInResult.let {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {
                        return googleAuthUiClient.handleGoogleSignIn(it.data!!)
                    } else {
                        return Resource.Error("credential data is null")
                    }
                }

                is Resource.Error -> {
                    return Resource.Error("" + it.message!!)
                }

                is Resource.Loading -> {
                    return Resource.Loading()
                }
            }
        }
    }


    suspend fun googleFirebaseSignOutRepo() {
        googleAuthUiClient.googleFirebaseSignOut()
        if (checkIfGoogleFirebaseUserIsSignedInRepo()) {
            dataPreferences.setApiToken("")
        }
    }

    fun checkIfGoogleFirebaseUserIsSignedInRepo(): Boolean {
        return googleAuthUiClient.checkIfGoogleFirebaseUserIsSignedIn()
    }
}