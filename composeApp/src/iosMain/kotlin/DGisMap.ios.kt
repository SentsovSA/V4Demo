import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import di.DGisProtocol
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.compose.koinInject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun DGisMap(
    modifier: Modifier,
    enabled: Boolean,
    zoom: Float,
    location: LatLng?,
    startPosition: LatLng?,
    points: List<PointMapModel>,
    onPointClick: (id: Long) -> Unit,
    bottomFocusAreaPadding: Int,
    onDragged: () -> Unit
) {
val dGisProtocol = koinInject<DGisProtocol>().apply {
    //TODO(NOT YET IMPLEMENTED)
}

    UIKitView(
        factory = {
            dGisProtocol.viewController.view
        },
        modifier = modifier.fillMaxSize(),
        update = {

        }
    )

    LaunchedEffect(points) {
        dGisProtocol.updatePointsCollection(points)
    }
}