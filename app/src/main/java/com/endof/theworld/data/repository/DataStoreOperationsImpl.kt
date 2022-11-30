package com.endof.theworld.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.endof.theworld.domain.model.GameLevel
import com.endof.theworld.domain.repository.DataStoreOperations
import com.endof.theworld.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = Constants.PREFERENCES_NAME)

class DataStoreOperationsImpl(context: Context) : DataStoreOperations {

    private companion object {
        //        val SFXSettingsKey = booleanPreferencesKey(name = Constants.PREFERENCES_SFX_ENABLED_KEY)
        val musicSettingsKey = booleanPreferencesKey(name = Constants.PREFERENCES_MUSIC_ENABLED_KEY)
        val lastLevelKey = stringPreferencesKey(name = Constants.PREFERENCES_LAST_LEVEL_KEY)
        val remainHealKey = intPreferencesKey(name = Constants.PREFERENCES_REMAIN_HEAL_KEY)
    }

    private val dataStore = context.dataStore

    override suspend fun saveMusicSettings(enabled: Boolean) {
        dataStore.edit { preferences ->
            preferences[musicSettingsKey] = enabled
        }
    }

    override suspend fun saveLastLevel(level: GameLevel) {
        dataStore.edit { preferences ->
            preferences[lastLevelKey] = level.name
        }
    }

    override suspend fun saveRemainHeal(healCount: Int) {
        dataStore.edit { preferences ->
            preferences[remainHealKey] = healCount
        }
    }

    override fun readMusicSettings(): Flow<Boolean> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[musicSettingsKey] ?: true
            }
    }

    override fun readLastLevel(): Flow<GameLevel> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                GameLevel.valueOf(preferences[lastLevelKey] ?: GameLevel.ONE.name)
            }
    }

    override fun readRemainHeal(): Flow<Int> {
        return dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[remainHealKey] ?: Constants.HEAL_MAX_NUMBER
            }

    }
}