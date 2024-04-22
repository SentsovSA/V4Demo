import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset

@Composable
expect fun DGisMap(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    zoom: Float = 14f,
    location: LatLng? = null,
    startPosition: LatLng? = null,
    points: List<PointMapModel>,
    selectedMarkerId: Int? = null,
    onPointClick: (id: Int) -> Unit,
    bottomFocusAreaPadding: Int,
    onDragged: () -> Unit
)


expect fun getSelectedId(): Int?
