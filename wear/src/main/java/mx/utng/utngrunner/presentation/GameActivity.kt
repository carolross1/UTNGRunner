package mx.utng.utngrunner.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import mx.utng.utngrunner.presentation.game.GameScreen
import mx.utng.utngrunner.presentation.game.GameViewModel
import mx.utng.utngrunner.presentation.game.GameViewModelFactory
import mx.utng.utngrunner.presentation.theme.WearAppTheme

// En GameActivity.kt — usar la factory
class GameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: GameViewModel = viewModel(
                factory = GameViewModelFactory(applicationContext)
            )
            WearAppTheme { GameScreen(viewModel = viewModel) }
        }
    }
}
