package core.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BottomSheetDragHandler(
    modifier: Modifier = Modifier,
    color: Color = Color.Gray,
    strokeWidth: Dp = 8.dp,
    cap: StrokeCap = StrokeCap.Round
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
    ) {
        drawLine(
            color = color,
            start = Offset(center.x - 16.dp.toPx(), 0f),
            end = Offset(center.x + 16.dp.toPx(), 0f),
            strokeWidth = strokeWidth.toPx(),
            cap = cap,
        )
    }
}
