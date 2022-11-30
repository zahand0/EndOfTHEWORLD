package com.endof.theworld.domain

import android.util.Log
import com.endof.theworld.domain.model.AttackResult
import com.endof.theworld.domain.model.GameLevel
import com.endof.theworld.domain.model.GameResult
import com.endof.theworld.domain.model.GameState
import com.endof.theworld.domain.model.entity.Enemy
import com.endof.theworld.domain.model.entity.Player
import com.endof.theworld.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.math.min

class GameEngine(
    private val coroutineScope: CoroutineScope
) {

    companion object {
        private const val TAG = "GameEngine"
    }

    private val _player = MutableStateFlow(Player(this::onPlayerDeath))
    val player: StateFlow<Player> = _player
    private var _enemy = MutableStateFlow(selectEnemy(GameLevel.ONE))
    val enemy: StateFlow<Enemy> = _enemy
    private val _gameResult = MutableStateFlow(GameResult.PLAYING)
    val gameResult: StateFlow<GameResult> = _gameResult
    private var onEnemyAttack: (AttackResult) -> Unit = {}
    private var healNumber = Constants.HEAL_MAX_NUMBER


    private var _gameState = MutableStateFlow(
        GameState(
            playerHp = _player.value.health,
            enemyHp = _enemy.value.health,
            playerTurn = true,
            healCount = healNumber
        )
    )
    val gameState: StateFlow<GameState> = _gameState

    fun start(
        level: GameLevel,
        healCount: Int,
        onEnemyAttack: (AttackResult) -> Unit
    ) {
        healNumber = min(Constants.HEAL_MAX_NUMBER, healCount)
        this.onEnemyAttack = onEnemyAttack
        _gameResult.value = GameResult.PLAYING
        _player.value.healToMax()
        _enemy.value = selectEnemy(level)
        _gameState.value = GameState(
            playerHp = _player.value.health,
            enemyHp = _enemy.value.health,
            playerTurn = true,
            healCount = healNumber
        )

    }

    fun playerHeal() {
        if (healNumber > 0) {
            healNumber--
            _player.value.healHalfHP()
            _gameState.value = _gameState.value.copy(
                playerHp = _player.value.health,
                playerTurn = false,
                healCount = healNumber
            )
            enemyTurn()
        }
    }

    fun playerAttack(): AttackResult {
        val enemyDf = _enemy.value.stats.defence
        val attackResult = _player.value.dealDamage(enemyDf)
        when (attackResult) {
            AttackResult.Failure -> {
                Log.d(TAG, "player missed")
                _gameState.value = _gameState.value.copy(
                    playerTurn = false
                )
            }
            is AttackResult.Success -> {
                _enemy.value.receiveDamage(attackResult.dmg)
                _gameState.value = _gameState.value.copy(
                    enemyHp = _enemy.value.health,
                    playerTurn = false
                )
                Log.d(
                    TAG, "enemy received ${attackResult.dmg} hp: ${_enemy.value.health}/" +
                            "${_enemy.value.stats.maxHealth}"
                )
            }
        }
        enemyTurn()
        return attackResult
    }

    private fun enemyTurn() {
        if (_gameResult.value == GameResult.PLAYING) {
            coroutineScope.launch {
                delay(1000)
                val playerDf = _player.value.stats.defence
                val attackResult = _enemy.value.dealDamage(playerDf)
                when (attackResult) {
                    AttackResult.Failure -> {
                        _gameState.value = _gameState.value.copy(
                            playerTurn = true
                        )
                    }
                    is AttackResult.Success -> {
                        _player.value.receiveDamage(attackResult.dmg)
                        _gameState.value = _gameState.value.copy(
                            playerHp = _player.value.health,
                            playerTurn = true
                        )
                    }
                }
                onEnemyAttack(attackResult)
            }
        }
    }

    private fun onPlayerDeath() {
        _gameResult.value = GameResult.LOSE
    }

    private fun onEnemyDeath() {
        _gameResult.value = GameResult.WIN
    }

    private fun selectEnemy(level: GameLevel): Enemy {
        return when (level) {
            GameLevel.ONE -> Enemy.HolHorse(this::onEnemyDeath)
            GameLevel.TWO -> Enemy.VanillaIce(this::onEnemyDeath)
            GameLevel.THREE -> Enemy.Dio(this::onEnemyDeath)
        }
    }
}