package com.endof.theworld.domain.model.entity

import com.endof.theworld.util.Constants

sealed class Enemy(stats: EntityStats, onDeath: () -> Unit) :
    Entity(stats = stats, onDeath = onDeath) {
    class HolHorse(onDeath: () -> Unit) : Enemy(
        stats = EntityStats(
            attack = Constants.HOL_HORSE_ATTACK,
            defence = Constants.HOL_HORSE_DEFENCE,
            maxHealth = Constants.HOL_HORSE_HEALTH,
            damage = Constants.HOL_HORSE_DAMAGE_RANGE
        ),
        onDeath = onDeath
    )
    class VanillaIce(onDeath: () -> Unit) : Enemy(
        stats = EntityStats(
            attack = Constants.VANILLA_ICE_ATTACK,
            defence = Constants.VANILLA_ICE_DEFENCE,
            maxHealth = Constants.VANILLA_ICE_HEALTH,
            damage = Constants.VANILLA_ICE_DAMAGE_RANGE
        ),
        onDeath = onDeath
    )
    class Dio(onDeath: () -> Unit) : Enemy(
        stats = EntityStats(
            attack = Constants.DIO_ATTACK,
            defence = Constants.DIO_DEFENCE,
            maxHealth = Constants.DIO_HEALTH,
            damage = Constants.DIO_DAMAGE_RANGE
        ),
        onDeath = onDeath
    )
}