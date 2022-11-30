package com.endof.theworld.presentation.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endof.theworld.data.repository.SoundManager
import com.endof.theworld.domain.GameEngine
import com.endof.theworld.domain.model.AttackResult
import com.endof.theworld.domain.model.GameLevel
import com.endof.theworld.domain.repository.DataStoreOperations
import com.endof.theworld.presentation.util.soundAttackId
import com.endof.theworld.presentation.util.soundEvadeId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameScreenViewModel @Inject constructor(
    private val dataStore: DataStoreOperations,
    private val soundManager: SoundManager
) : ViewModel() {
    companion object {
        private const val TAG = "GameScreenViewModel"
    }

    private val gameEngine = GameEngine(coroutineScope = viewModelScope)

    val player = gameEngine.player
    val enemy = gameEngine.enemy
    val gameResult = gameEngine.gameResult
    val gameState = gameEngine.gameState
    val lastLevel = dataStore.readLastLevel()
    val remainHeal = dataStore.readRemainHeal()

    private val _enemyMsg = MutableSharedFlow<String>()
    val enemyMsg: SharedFlow<String> = _enemyMsg
    private val _playerMsg = MutableSharedFlow<String>()
    val playerMsg: SharedFlow<String> = _playerMsg

    private var _gameStarted = false
    val gameStarted get() = _gameStarted
    fun start(
        level: GameLevel,
        healCount: Int
    ) {
        _gameStarted = true
        gameEngine.start(
            level = level,
            healCount = healCount,
            onEnemyAttack = this::onEnemyAttack
        )
    }

    fun playerAttack() {
        when (val attackResult = gameEngine.playerAttack()) {
            AttackResult.Failure -> {
                playSound(enemy.value.soundEvadeId())
                viewModelScope.launch {
                    _enemyMsg.emit("Miss")
                    delay(400)
                    _enemyMsg.emit("")
                }
            }
            is AttackResult.Success -> {
                playSound(player.value.soundAttackId())
                viewModelScope.launch {

                    _enemyMsg.emit("-${attackResult.dmg}")
                    delay(400)
                    _enemyMsg.emit("")
                }
            }
        }
    }

    private fun onEnemyAttack(attackResult: AttackResult) {
        when (attackResult) {
            AttackResult.Failure -> {
                playSound(player.value.soundEvadeId())
                viewModelScope.launch {
                    _playerMsg.emit("Miss")
                    delay(400)
                    _playerMsg.emit("")
                }
            }
            is AttackResult.Success -> {
                playSound(enemy.value.soundAttackId())
                viewModelScope.launch {
                    _playerMsg.emit("-${attackResult.dmg}")
                    delay(400)
                    _playerMsg.emit("")
                }
            }
        }
    }

    fun playSound(soundId: Int) {
        soundManager.playSound(soundId)
    }

    fun playerHeal() {
        gameEngine.playerHeal()
    }

    fun isMusicEnabled(): Flow<Boolean> {
        return dataStore.readMusicSettings()
    }

    fun updateLevel(level: GameLevel) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveLastLevel(level)
        }
    }

    fun updateRemainHeal(healCount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveRemainHeal(healCount)
        }
    }
}