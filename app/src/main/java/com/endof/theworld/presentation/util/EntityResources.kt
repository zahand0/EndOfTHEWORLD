package com.endof.theworld.presentation.util

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.endof.theworld.R
import com.endof.theworld.domain.model.entity.Enemy
import com.endof.theworld.domain.model.entity.Player

@DrawableRes
fun Enemy.imgId(): Int {
    return when (this) {
        is Enemy.Dio -> R.drawable.dio
        is Enemy.HolHorse -> R.drawable.hol_horse
        is Enemy.VanillaIce -> R.drawable.vanilla_ice
    }
}

@DrawableRes
fun Enemy.imgLoseId(): Int {
    return when (this) {
        is Enemy.Dio -> R.drawable.dio_lose
        is Enemy.HolHorse -> R.drawable.hol_horse_lose
        is Enemy.VanillaIce -> R.drawable.vanilla_ice_lose
    }
}

@DrawableRes
fun Enemy.imgWinId(): Int {
    return when (this) {
        is Enemy.Dio -> R.drawable.dio_win
        is Enemy.HolHorse -> R.drawable.hol_horse_win
        is Enemy.VanillaIce -> R.drawable.vanilla_ice_win
    }
}

@DrawableRes
fun Enemy.soundAttackId(): Int {
    return when (this) {
        is Enemy.Dio -> R.raw.dio_attack
        is Enemy.HolHorse -> R.raw.hol_horse_attack
        is Enemy.VanillaIce -> R.raw.vanilla_ice_attack
    }
}

@RawRes
fun Enemy.soundWinId(): Int {
    return when (this) {
        is Enemy.Dio -> R.raw.dio_win
        is Enemy.HolHorse -> R.raw.hol_horse_win
        is Enemy.VanillaIce -> R.raw.vanilla_ice_win
    }
}

@RawRes
fun Enemy.soundEvadeId(): Int {
    return when (this) {
        is Enemy.Dio -> R.raw.dio_evade
        is Enemy.HolHorse -> R.raw.hol_horse_evade
        is Enemy.VanillaIce -> R.raw.vanilla_ice_evade
    }
}

fun Enemy.name(): String {
    return when (this) {
        is Enemy.Dio -> "Dio"
        is Enemy.HolHorse -> "Hol Horse"
        is Enemy.VanillaIce -> "Vanilla Ice"
    }
}

@DrawableRes
fun Player.imgId(): Int {
    return R.drawable.jotaro
}

@DrawableRes
fun Player.imgLoseId(): Int {
    return R.drawable.jotaro_lose
}

@DrawableRes
fun Player.imgWinId(): Int {
    return R.drawable.jotaro_win
}

@RawRes
fun Player.soundAttackId(): Int {
    return R.raw.jotaro_attack
}

@RawRes
fun Player.soundWinId(): Int {
    return R.raw.jotaro_win
}

@RawRes
fun Player.soundEvadeId(): Int {
    return R.raw.jotaro_evade
}

fun Player.name(): String {
    return "Jotaro"
}