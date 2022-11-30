package com.endof.theworld.util

object Constants {

    const val PREFERENCES_NAME = "game_preferences"
    const val PREFERENCES_SFX_ENABLED_KEY = "sfx_enabled"
    const val PREFERENCES_MUSIC_ENABLED_KEY = "music_enabled"
    const val PREFERENCES_LAST_LEVEL_KEY = "last_level"
    const val PREFERENCES_REMAIN_HEAL_KEY = "remain_heal"

    const val DICE_SIDES_NUMBER = 6
    val DICE_SUCCESS_RANGE = 5..DICE_SIDES_NUMBER

    const val HEAL_MAX_NUMBER = 3

    // Player Stats
    const val PLAYER_ATTACK = 12
    const val PLAYER_DEFENCE = 9
    const val PLAYER_HEALTH = 100
    val PLAYER_DAMAGE_RANGE = 30..40

    // Hol Horse Stats
    const val HOL_HORSE_ATTACK = 9
    const val HOL_HORSE_DEFENCE = 9
    const val HOL_HORSE_HEALTH = 80
    val HOL_HORSE_DAMAGE_RANGE = 20..32

    // Vanilla Ice Stats
    const val VANILLA_ICE_ATTACK = 10
    const val VANILLA_ICE_DEFENCE = 10
    const val VANILLA_ICE_HEALTH = 90
    val VANILLA_ICE_DAMAGE_RANGE = 25..35

    // Dio Stats
    const val DIO_ATTACK = 12
    const val DIO_DEFENCE = 11
    const val DIO_HEALTH = 100
    val DIO_DAMAGE_RANGE = 30..45
}