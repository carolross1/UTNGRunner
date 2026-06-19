package mx.utng.utngrunner.presentation.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.wear.compose.material.Colors
import androidx.wear.compose.material.MaterialTheme

private val UtngRunnerColorPalette = Colors(
    primary = Color(0xFFF9A825),
    primaryVariant = Color(0xFFC17900),
    secondary = Color(0xFF1A237E),
    secondaryVariant = Color(0xFF0D1B4A),
    error = Color(0xFFE53935),
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onError = Color.White,
)

/** Tema simple de la app, usado por GameActivity (PASO 9 / PASO 10). */
@Composable
fun WearAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = UtngRunnerColorPalette,
        content = content
    )
}
