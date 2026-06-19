package mx.utng.utngrunner.presentation.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text

/**
 * Overlay de fin de partida. Referenciado desde GameScreen (PASO 7).
 * Misma responsabilidad única que IdleOverlay: solo presentación, sin lógica de juego.
 */
@Composable
fun GameOverOverlay(score: Int, highScore: Int, onRetry: () -> Unit) {
    Box(
        Modifier.fillMaxSize().background(Color(0xCC000000))
            .clickable(onClick = onRetry),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("GAME OVER", style = MaterialTheme.typography.title3,
                 color = Color(0xFFE53935), fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(6.dp))
            Text("Puntaje: $score", style = MaterialTheme.typography.body2,
                 color = Color.White)
            Text("Récord: $highScore", style = MaterialTheme.typography.body2,
                 color = Color(0xFFF9A825))
            Spacer(Modifier.height(8.dp))
            Text("Toca para reintentar", style = MaterialTheme.typography.caption2,
                 color = Color.White.copy(alpha = 0.7f))
        }
    }
}
