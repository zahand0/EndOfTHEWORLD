package com.endof.theworld.domain.model.entity

data class EntityStats(
    val attack: Int,
    val defence: Int,
    var maxHealth: Int,
    val damage: IntRange
)
