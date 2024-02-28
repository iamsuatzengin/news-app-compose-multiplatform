package core.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun IconText(
    text: String,
    icon: ImageVector,
    iconTint: Color = Color.Gray,
    fontWeight: FontWeight = FontWeight.Normal,
    marginBetween: Dp = 8.dp
) {
    Row {
        Icon(
            imageVector = icon,
            contentDescription = "Icon",
            tint = iconTint
        )

        Spacer(modifier = Modifier.width(width = marginBetween))

        Text(text = text, color = Color.Gray, fontWeight = fontWeight)
    }
}
