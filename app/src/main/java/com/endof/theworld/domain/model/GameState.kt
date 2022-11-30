package com.endof.theworld.domain.model

data class GameState(
    val playerHp: Int,
    val enemyHp: Int,
    val playerTurn: Boolean,
    val healCount: Int
)
