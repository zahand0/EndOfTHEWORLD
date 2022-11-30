package com.endof.theworld.presentation.screens.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.endof.theworld.domain.model.GameLevel
import com.endof.theworld.domain.repository.DataStoreOperations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuScreenViewModel @Inject constructor(
    private val dataStore: DataStoreOperations
) : ViewModel() {
    companion object {
        private const val TAG = "MenuScreenViewModel"
    }

    val lastLevel = dataStore.readLastLevel()

    fun isMusicEnabled(): Flow<Boolean> {
        return dataStore.readMusicSettings()
    }

    fun setMusicSettings(enabled: Boolean) {
        viewModelScope.launch {
            dataStore.saveMusicSettings(enabled)
        }
    }

    fun updateLevel(level: GameLevel) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveLastLevel(level)
        }
    }

    fun updateRemainHeal(healCount: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStore.saveRemainHeal(healCount)
        }
    }
}