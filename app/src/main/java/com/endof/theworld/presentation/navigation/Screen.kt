package com.endof.theworld.presentation.navigation

sealed class Screen(val route: String) {
    object Menu : Screen(route = "menu_screen")
    object Game : Screen(route = "game_screen")
    object Settings : Screen(route = "settings_screen")
}
