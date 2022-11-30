package com.endof.theworld.domain.repository

import com.endof.theworld.domain.model.GameLevel
import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {

    suspend fun saveMusicSettings(enabled: Boolean)

    suspend fun saveLastLevel(level: GameLevel)

    suspend fun saveRemainHeal(healCount: Int)

    fun readMusicSettings(): Flow<Boolean>

    fun readLastLevel(): Flow<GameLevel>

    fun readRemainHeal(): Flow<Int>
}