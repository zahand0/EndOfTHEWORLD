package com.endof.theworld.domain.model.entity

import com.endof.theworld.domain.model.AttackResult
import com.endof.theworld.util.Constants
import kotlin.math.max

abstract class Entity(
    val stats: EntityStats,
    private val onDeath: () -> Unit
) {

    init {
        if (stats.attack < 0) {
            throw IllegalArgumentException("Attack must be non-negative")
        }
        if (stats.defence < 0) {
            throw IllegalArgumentException("Defence must be non-negative")
        }
        if (stats.maxHealth <= 0) {
            throw IllegalArgumentException("Max health must be positive")
        }
    }

    protected var _health: Int = stats.maxHealth
    val health get() = _health

    fun receiveDamage(dmg: Int) {
        if (dmg > 0) {
            _health = max(0, _health - dmg)
            if (isDead()) {
                onDeath()
            }
        }
    }

    fun dealDamage(enemyDf: Int): AttackResult {
        val attackModifier = max(1, stats.attack - enemyDf + 1)
        repeat(attackModifier) {
            if (diceRollResult()) {
                return AttackResult.Success(dmg = stats.damage.random())
            }
        }
        return AttackResult.Failure
    }

    private fun diceRollResult(): Boolean {
        val roll = (1..Constants.DICE_SIDES_NUMBER).random()
        return roll in Constants.DICE_SUCCESS_RANGE
    }


    private fun isDead(): Boolean {
        return _health <= 0
    }
}