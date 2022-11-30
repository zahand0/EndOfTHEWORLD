package com.endof.theworld.domain.model

sealed class AttackResult {
    class Success(val dmg: Int) : AttackResult()
    object Failure : AttackResult()
}