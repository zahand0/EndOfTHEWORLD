package com.endof.theworld.domain.model

enum class GameLevel {
    ONE, TWO, THREE;
    companion object {
        fun lastLevel(): GameLevel {
            return THREE
        }
    }
}

fun GameLevel.nextLevel(): GameLevel {
    return when(this) {
        GameLevel.ONE -> GameLevel.TWO
        GameLevel.TWO -> GameLevel.THREE
        GameLevel.THREE -> GameLevel.ONE
    }
}

