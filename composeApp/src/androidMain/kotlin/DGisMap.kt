import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import org.activity.v4d.demo.MapController
import org.activity.v4d.demo.getMapView
import ru.dgis.sdk.Duration
import ru.dgis.sdk.coordinates.GeoPoint
import ru.dgis.sdk.map.CameraAnimationType
import ru.dgis.sdk.map.CameraPosition
import ru.dgis.sdk.map.Zoom

private var mapController: MutableState<MapController> = mutableStateOf(MapController(null, null))
@Composable
actual fun DGisMap(
    modifier: Modifier,
    enabled: Boolean,
    zoom: Float,
    location: LatLng?,
    startPosition: LatLng?,
    points: List<PointMapModel>,
    selectedMarkerId: Int?,
    onPointClick: (id: Int) -> Unit,
    bottomFocusAreaPadding: Int,
    onDragged: () -> Unit
) {

    val context = LocalContext.current
    val mapView = getMapView(context)
    var myLocation by remember { mutableStateOf(LatLng(0.0, 0.0)) }
    mapController = remember { mutableStateOf(MapController(context, mapView)) }
    onPointClick.let {
        mapController.value.onPointClick = it
    }

    val requestPermissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                getLocation(context) { updatedLocation ->
                    myLocation = updatedLocation
                    mapController.value.updateLocation(myLocation)
                    Log.i("", "locationGrant: ${myLocation.lat} ${myLocation.lng}")
                }
            }
        }
    DisposableEffect(myLocation) {
        if (myLocation.lat == 0.0 && myLocation.lng == 0.0) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                getLocation(context) { initialLocation ->
                    myLocation = initialLocation
                    mapController.value.updateLocation(myLocation)
                    Log.i("", "locationDispEf: ${myLocation.lat} ${myLocation.lng}")
                }
            } else {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
        onDispose { }
    }

    if (LocalInspectionMode.current) {
        Box(modifier = modifier)
        return
    }

    AndroidView(modifier = modifier, factory = { mapView }) {
        val cameraPosition = CameraPosition(
            GeoPoint(
                location!!.lat, location.lng
            ),
            zoom = Zoom(15f)
        )
        mapView.getMapAsync {
            it.camera.move(cameraPosition, Duration.ofSeconds(1), CameraAnimationType.DEFAULT)
        }
    }

    LaunchedEffect(points) {
        mapController.value.updatePointsCollection(points)
    }
}

fun getLocation(context: Context, callback: (LatLng) -> Unit) {
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000).build()

    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        fusedLocationClient.requestLocationUpdates(locationRequest, object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val latestLocation = locationResult.lastLocation
                if (latestLocation != null) {
                    val myLocation = LatLng(latestLocation.latitude, latestLocation.longitude)
                    callback(myLocation)
                }
                fusedLocationClient.removeLocationUpdates(this)
            }
        }, Looper.getMainLooper())
    }
}

actual fun getSelectedId(): Int? {
    Log.i("", "zhopa${mapController.value.getMarkerId()}")
   return mapController.value.getMarkerId()
}