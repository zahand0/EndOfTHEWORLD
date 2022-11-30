package com.endof.theworld.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.endof.theworld.presentation.screens.game.GameScreen
import com.endof.theworld.presentation.screens.menu.MenuScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Menu.route
    ) {
        composable(route = Screen.Menu.route) {
            MenuScreen(
                navController = navController
            )
        }
        composable(
            route = Screen.Game.route,
            enterTransition = { scaleIn() },
            exitTransition = { scaleOut() + fadeOut() }
        ) {
            GameScreen(navController = navController)
        }
        composable(
            route = Screen.Settings.route,
            enterTransition = { slideInVertically(initialOffsetY = { it }) },
            exitTransition = { slideOutVertically(targetOffsetY = { it }) }
        ) {
//            SettingsScreen(navController = navController)
        }

    }
}