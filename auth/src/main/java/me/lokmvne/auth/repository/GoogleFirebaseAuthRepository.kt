package me.lokmvne.auth.repository

import android.content.Context
import me.lokmvne.auth.data.GoogleAuthUiClient
import me.lokmvne.common.data.DataPreferences
import me.lokmvne.common.utils.Resource
import javax.inject.Inject

class GoogleFirebaseAuthRepository @Inject constructor(
    private val googleAuthUiClient: GoogleAuthUiClient,
    private val dataPreferences: DataPreferences,
) {
    suspend fun handleGoogleSignInRepo(
        context: Context
    ): Resource<me.lokmvne.auth.data.model.GoogleUser> {
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