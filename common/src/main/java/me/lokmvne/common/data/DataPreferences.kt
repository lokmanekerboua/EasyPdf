package me.lokmvne.common.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.easyPDFpreferences: DataStore<Preferences> by preferencesDataStore(name = "easyPDF_preferences")

class DataPreferences @Inject constructor(@ApplicationContext context: Context) {
    private val easyPDFpreferences = context.easyPDFpreferences

    private object Keys {
        val onboardingCompleted = booleanPreferencesKey("onboardingCompleted")
        val isDarkMode = booleanPreferencesKey("isDarkMode")
        val isAutoDarkMode = booleanPreferencesKey("isAutoDarkMode")
        val lastScreen = stringPreferencesKey("lastScreen")
        val apitoken = stringPreferencesKey("apitoken")
    }

    suspend fun setApiToken(token: String) {
        easyPDFpreferences.edit { _preferences ->
            _preferences[Keys.apitoken] = token
        }
    }

    fun readApiToken(): Flow<String> {
        return easyPDFpreferences.data
            .catch {
                if (it is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }
            .map { settings ->
                settings[Keys.apitoken] ?: ""
            }
    }

    suspend fun setOnboardingCompleted() {
        easyPDFpreferences.edit { _preferences ->
            _preferences[Keys.onboardingCompleted] = true
        }
    }

    fun readOnboardingCompleted(): Flow<Boolean> {
        return easyPDFpreferences.data
            .catch {
                if (it is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }
            .map { settings ->
                settings[Keys.onboardingCompleted] ?: false
            }
    }
}