package mx.utng.utngrunner.presentation.game

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.sp
import mx.utng.utngrunner.domain.model.Coin
import mx.utng.utngrunner.domain.model.GameState
import mx.utng.utngrunner.domain.model.Obstacle
import mx.utng.utngrunner.domain.model.Player
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.sin

/** GameRenderer: SOLO dibuja. No toca la lógica de juego. */
object GameRenderer {

    private val COLORS = GameColors()

    fun draw(canvas: Canvas, size: Size, state: GameState, frame: Long) {
        drawBackground(canvas, size)
        drawGround(canvas, size)
        drawCoins(canvas, state.coins, frame)
        drawObstacles(canvas, state.obstacles)
        drawPlayer(canvas, state.player, frame)
        drawHUD(canvas, size, state)
    }

    private fun drawBackground(canvas: Canvas, size: Size) {
        val paint = Paint().apply {
            shader = LinearGradientShader(
                from = Offset(0f, 0f), to = Offset(0f, size.height),
                colors = listOf(Color(0xFF0D1B4A), Color(0xFF1A237E))
            )
        }
        canvas.drawRect(Rect(Offset.Zero, size), paint)
    }

    private fun drawGround(canvas: Canvas, size: Size) {
        val groundY = Player.FLOOR_Y + 20f
        val paint = Paint().apply { color = COLORS.ground }
        canvas.drawRect(
            Rect(Offset(0f, groundY), Offset(size.width, size.height)), paint
        )
        val linePaint = Paint().apply { color = Color(0xFFF9A825) }
        canvas.drawRect(Rect(Offset(0f, groundY), Offset(size.width, groundY + 2f)), linePaint)
    }

    private fun drawCoins(canvas: Canvas, coins: List<Coin>, frame: Long) {
        val paint = Paint().apply { color = COLORS.coin }
        coins.filter { !it.collected }.forEach { coin ->
            val bob = sin((frame + coin.x) * 0.1f) * 4f
            canvas.drawCircle(Offset(coin.x, coin.y + bob), 6f, paint)
        }
    }

    private fun drawObstacles(canvas: Canvas, obstacles: List<Obstacle>) {
        val floor = Player.FLOOR_Y + 20f
        obstacles.forEach { o ->
            val paint = Paint().apply {
                color = when (o.type.label) {
                    "TAREA"  -> Color(0xFFD32F2F)
                    "EXAMEN" -> Color(0xFFC2185B)
                    "BUG"    -> Color(0xFF388E3C)
                    else     -> Color(0xFF455A64)
                }
            }
            canvas.drawRect(
                Rect(
                    Offset(o.x, floor - o.height),
                    Offset(o.x + o.width, floor)
                ),
                paint
            )
        }
    }

    private fun drawPlayer(canvas: Canvas, player: Player, frame: Long) {
        val alpha = if (player.isInvincible && (frame / 4) % 2 == 0L) 0.3f else 1f
        val legSwing = if (player.isJumping) 0f else sin(frame * 0.3f) * 8f
        val yPos = player.y

        val bodyPaint = Paint().apply {
            color = COLORS.player.copy(alpha = alpha)
        }
        canvas.drawRect(Rect(Offset(player.x - 6f, yPos - 10f), Offset(player.x + 14f, yPos + 14f)), bodyPaint)

        val helmetPaint = Paint().apply { color = Color(0xFF1A237E).copy(alpha = alpha) }
        canvas.drawRect(Rect(Offset(player.x - 5f, yPos - 24f), Offset(player.x + 13f, yPos - 14f)), helmetPaint)

        val legPaint = Paint().apply { color = Color(0xFF3E2723).copy(alpha = alpha) }
        canvas.drawRect(
            Rect(Offset(player.x - 4f + legSwing * 0.2f, yPos + 14f), Offset(player.x + 2f + legSwing * 0.2f, yPos + 22f)),
            legPaint
        )
        canvas.drawRect(
            Rect(Offset(player.x + 6f - legSwing * 0.2f, yPos + 14f), Offset(player.x + 12f - legSwing * 0.2f, yPos + 22f)),
            legPaint
        )
    }

    private fun drawHUD(canvas: Canvas, size: Size, state: GameState) {
        // Se obtiene el nativeCanvas UNA SOLA VEZ aquí y se pasa a drawCenteredText
        val nativeCanvas = canvas.nativeCanvas
        val cx = size.width / 2f
        drawCenteredText(nativeCanvas, getSystemTime(), cx, 22f, 14.sp.value)
        drawCenteredText(nativeCanvas, "${state.score} pts", cx, size.height - 14f, 11.sp.value)
        repeat(state.lives) { i ->
            drawHeart(canvas, 8f + i * 16f, 36f)
        }
    }

    // ✅ CORRECCIÓN: recibe android.graphics.Canvas (nativeCanvas) en lugar de androidx.compose.ui.graphics.Canvas
    private fun drawCenteredText(
        canvas: android.graphics.Canvas,
        text: String,
        cx: Float,
        y: Float,
        textSizeSp: Float
    ) {
        val paint = android.graphics.Paint().apply {
            color = android.graphics.Color.WHITE
            textSize = textSizeSp * 2.2f
            textAlign = android.graphics.Paint.Align.CENTER
            isAntiAlias = true
        }
        canvas.drawText(text, cx, y, paint)
    }

    private fun drawHeart(canvas: Canvas, x: Float, y: Float) {
        val paint = Paint().apply { color = Color(0xFFE53935) }
        canvas.drawCircle(Offset(x - 3f, y), 4f, paint)
        canvas.drawCircle(Offset(x + 3f, y), 4f, paint)
        canvas.drawRect(Rect(Offset(x - 6f, y), Offset(x + 6f, y + 6f)), paint)
    }

    private fun getSystemTime(): String =
        SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
}

/** Paleta de colores centralizada del juego */
class GameColors {
    val sky = Color(0xFF1A237E)
    val ground = Color(0xFF263238)
    val player = Color(0xFFE65100)
    val coin = Color(0xFFFFD700)
}