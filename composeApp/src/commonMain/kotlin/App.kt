import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.FloatingActionButtonElevation
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.lighthousegames.logging.logging


@Composable
@Preview
fun App() {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxSize()) {
            DGisMap(
                modifier = Modifier.fillMaxSize(),
                enabled = true,
                zoom = 15f,
                location = LatLng(55.759616, 37.641530),
                startPosition = LatLng(55.759616, 37.641530),
                points = listOf(
                    PointMapModel(
                        id = 1,
                        latitude = 55.752425,
                        longitude = 37.613983,
                        visible = true
                    ),
                    PointMapModel(
                        id = 2,
                        latitude = 55.729065,
                        longitude = 37.620507,
                        visible = true
                    ),
                    PointMapModel(
                        id = 3,
                        latitude = 55.709065,
                        longitude = 37.610507,
                        visible = true
                    ),
                ),
                bottomFocusAreaPadding = 0,
                onPointClick = {
                    logging().i{"onPointClick $it"}
                },
                onDragged = {}
            )
            Button(
                onClick = {},
                modifier = Modifier
            ) {
                Text(text = "Test")
            }
        }
    }
}