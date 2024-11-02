package me.lokmvne.common.repository

import kotlinx.coroutines.flow.Flow
import me.lokmvne.common.data.DataPreferences
import javax.inject.Inject

class DataPreferencesRepository @Inject constructor(
    private val dataPreferences: DataPreferences
) {
    suspend fun saveOnboardingComplete() {
        dataPreferences.setOnboardingCompleted()
    }

    fun readOnboardingComplete(): Flow<Boolean> {
        return dataPreferences.readOnboardingCompleted()
    }
}