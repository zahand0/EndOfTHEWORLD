package com.endof.theworld.domain.model.entity

import com.endof.theworld.util.Constants
import kotlin.math.min

class Player(onDeath: () -> Unit) : Entity(
    stats = EntityStats(
        attack = Constants.PLAYER_ATTACK,
        defence = Constants.PLAYER_DEFENCE,
        maxHealth = Constants.PLAYER_HEALTH,
        damage = Constants.PLAYER_DAMAGE_RANGE
    ),
    onDeath = onDeath
) {

    private fun heal(amount: Int) {
        _health = min(_health + amount, stats.maxHealth)
    }

    fun healHalfHP() {
        heal(stats.maxHealth / 2)
    }

    fun healToMax() {
        _health = stats.maxHealth
    }
}