import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen

data class InfoScreen(val id: Int) : Screen {
    @Composable
    override fun Content() {
        Column(modifier = Modifier.fillMaxHeight(0.5f).fillMaxWidth()) {
            Text(text = "id = $id")
        }
    }
}