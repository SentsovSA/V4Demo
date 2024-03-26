import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun DGisMap(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    zoom: Float = 14f,
    location: LatLng? = null,
    startPosition: LatLng? = null,
    points: List<PointMapModel>,
    onPointClick: (id: Long) -> Unit,
    bottomFocusAreaPadding: Int,
    onDragged: () -> Unit
)
