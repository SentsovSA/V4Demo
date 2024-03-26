import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.viewinterop.AndroidView
import org.activity.v4d.demo.MainActivity
import org.activity.v4d.demo.MapController
import org.activity.v4d.demo.getMapView
import ru.dgis.sdk.coordinates.GeoPoint
import ru.dgis.sdk.coordinates.Latitude
import ru.dgis.sdk.map.CameraPosition
import ru.dgis.sdk.map.Zoom

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
    val context = LocalContext.current
    val mapView = getMapView(context)
    val mapController = remember { mutableStateOf(MapController(context, mapView)) }

    if (LocalInspectionMode.current) {
        Box(modifier = modifier)
        return
    }

    AndroidView(modifier = modifier, factory = { mapView }) {
        mapView.getMapAsync { map ->
            val cameraPosition = CameraPosition(GeoPoint(location!!.lat, location.lng), Zoom(zoom))
            map.camera.move(cameraPosition)
        }
    }

    LaunchedEffect(points) {
        mapController.value.updatePointsCollection(points)
    }

    LaunchedEffect(onPointClick) {
        mapController.value.appPointListener(onPointClick)
    }
}