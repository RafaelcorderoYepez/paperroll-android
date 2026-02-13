package com.example.paperroll_123.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Handles persistent user preferences such as disclaimer acceptance.
 */
class PreferencesManager(private val context: Context) {

    private val Context.dataStore by preferencesDataStore("settings")

    companion object {
        val DISCLAIMER_ACCEPTED = booleanPreferencesKey("disclaimer_accepted")
    }

    /**
     * Saves the user's acceptance of the disclaimer.
     */
    suspend fun setDisclaimerAccepted(value: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[DISCLAIMER_ACCEPTED] = value
        }
    }

    /**
     * Reads whether the user has already accepted the disclaimer.
     */
    val disclaimerAccepted: Flow<Boolean> =
        context.dataStore.data.map { prefs ->
            prefs[DISCLAIMER_ACCEPTED] ?: false
        }
}
