package me.lokmvne.easypdf.data

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import me.lokmvne.common.utils.Resource
import me.lokmvne.easypdf.BuildConfig
import me.lokmvne.easypdf.data.model.GoogleUser
import javax.inject.Inject

class GoogleAuthUiClient @Inject constructor(
    private val credentialManager: CredentialManager
) {
    private val fireBaseAuth = Firebase.auth

    suspend fun googleSignIn(
        //credentialManager: CredentialManager,
        context: Context
    ): Resource<GetCredentialResponse> {
        try {
            val credential = credentialManager.getCredential(
                request = buildGoogleSignInRequest(),
                context = context
            )
            return Resource.Success(credential)
        } catch (e: GetCredentialCancellationException) {
            //Log.e("TAGGGGG", "You Cancelled the sign in process: ${e.message}")
            return Resource.Error("You Cancelled the sign in process: ${e.message}")
        } catch (e: Exception) {
            return Resource.Error("An error occurred while signing in")
        }
    }

    suspend fun handleGoogleSignIn(credentialResponse: GetCredentialResponse): Resource<GoogleUser> {
        when (val credential = credentialResponse.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredentials =
                            GoogleIdTokenCredential.createFrom(credential.data)

                        val googleAuthCredential =
                            GoogleAuthProvider.getCredential(googleIdTokenCredentials.idToken, null)

                        val fireBaseUser =
                            fireBaseAuth.signInWithCredential(googleAuthCredential).await().user

                        if (fireBaseUser != null) {
                            val googleUser = GoogleUser(
                                name = fireBaseUser.displayName ?: "",
                                email = fireBaseUser.email ?: "",
                                photoUrl = fireBaseUser.photoUrl.toString()
                            )
                            return Resource.Success(
                                googleUser
                            )
                        } else {
                            return Resource.Error("The user is null")
                        }
                    } catch (e: Exception) {
                        return Resource.Error("An error occurred while signing in")
                    }
                } else {
                    return Resource.Error("Credential type is miss matched")
                }
            }

            else -> {
                return Resource.Error("Credential type isn't supported")
            }
        }
    }

    fun googleFirebaseSignOut() {
        fireBaseAuth.signOut()
    }

    fun checkIfGoogleFirebaseUserIsSignedIn(): Boolean {
        return fireBaseAuth.currentUser != null
    }


    private fun buildGoogleSignInRequest(): GetCredentialRequest {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setServerClientId(BuildConfig.WEB_CLIENT_ID)
            .setFilterByAuthorizedAccounts(false)
            .build()

        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }
}