package com.ipc.sentinela.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserPreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private object PreferencesKeys {
        val IS_SIMPLE_LANGUAGE_MODE = booleanPreferencesKey("is_simple_language_mode")
        val HAPTIC_FEEDBACK_ENABLED = booleanPreferencesKey("haptic_feedback_enabled")
        val SNOOZE_MODE_ACTIVE = booleanPreferencesKey("snooze_mode_active")
        val SELECTED_REGION = stringPreferencesKey("selected_region")
        val USER_CROPS = stringSetPreferencesKey("user_crops")
    }

    val userPreferencesFlow: Flow<UserPreferences> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            UserPreferences(
                isSimpleLanguageMode = preferences[PreferencesKeys.IS_SIMPLE_LANGUAGE_MODE] ?: true,
                hapticFeedbackEnabled = preferences[PreferencesKeys.HAPTIC_FEEDBACK_ENABLED] ?: true,
                snoozeModeActive = preferences[PreferencesKeys.SNOOZE_MODE_ACTIVE] ?: false,
                selectedRegion = preferences[PreferencesKeys.SELECTED_REGION] ?: "",
                userCrops = preferences[PreferencesKeys.USER_CROPS] ?: emptySet()
            )
        }

    suspend fun updateSimpleLanguageMode(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.IS_SIMPLE_LANGUAGE_MODE] = enabled
        }
    }

    suspend fun updateHapticFeedback(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.HAPTIC_FEEDBACK_ENABLED] = enabled
        }
    }

    suspend fun updateSnoozeMode(active: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SNOOZE_MODE_ACTIVE] = active
        }
    }

    suspend fun updateSelectedRegion(region: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SELECTED_REGION] = region
        }
    }

    suspend fun updateUserCrops(crops: Set<String>) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_CROPS] = crops
        }
    }
}

data class UserPreferences(
    val isSimpleLanguageMode: Boolean,
    val hapticFeedbackEnabled: Boolean,
    val snoozeModeActive: Boolean,
    val selectedRegion: String,
    val userCrops: Set<String>
)
