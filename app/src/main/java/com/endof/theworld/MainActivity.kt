package com.endof.theworld

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.endof.theworld.domain.repository.DataStoreOperations
import com.endof.theworld.presentation.navigation.SetupNavGraph
import com.endof.theworld.presentation.theme.EndOfTHEWORLDTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    @Inject
    lateinit var dataStore: DataStoreOperations

    private val activityVisible = mutableStateOf(false)

    private lateinit var mediaPlayer: MediaPlayer

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mediaPlayer = MediaPlayer.create(this, R.raw.background_music)
        mediaPlayer.isLooping = true
        setContent {
            val playMusic = dataStore.readMusicSettings().collectAsState(initial = true)
            LaunchedEffect(key1 = playMusic.value, key2 = activityVisible.value) {
                if (playMusic.value) {
                    if (!mediaPlayer.isPlaying)
                        mediaPlayer.start()
                } else {
                    mediaPlayer.pause()
                }
            }
            EndOfTHEWORLDTheme {
                navController = rememberAnimatedNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SetupNavGraph(
                        navController = navController
                    )
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (::mediaPlayer.isInitialized)
            mediaPlayer.pause()
    }

    override fun onResume() {
        super.onResume()
        activityVisible.value = !activityVisible.value
    }
}
