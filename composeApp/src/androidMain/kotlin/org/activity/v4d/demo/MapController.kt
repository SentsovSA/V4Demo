package org.activity.v4d.demo

import LatLng
import PointMapModel
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import ru.dgis.sdk.DGis
import ru.dgis.sdk.Duration
import ru.dgis.sdk.coordinates.GeoPoint
import ru.dgis.sdk.geometry.GeoPointWithElevation
import ru.dgis.sdk.map.BearingSource
import ru.dgis.sdk.map.CameraAnimationType
import ru.dgis.sdk.map.CameraPosition
import ru.dgis.sdk.map.MapObjectManager
import ru.dgis.sdk.map.MapView
import ru.dgis.sdk.map.Marker
import ru.dgis.sdk.map.MarkerOptions
import ru.dgis.sdk.map.MyLocationController
import ru.dgis.sdk.map.MyLocationMapObjectSource
import ru.dgis.sdk.map.ScreenDistance
import ru.dgis.sdk.map.ScreenPoint
import ru.dgis.sdk.map.TouchEventsObserver
import ru.dgis.sdk.map.Zoom
import ru.dgis.sdk.map.imageFromResource
import ru.dgis.sdk.map.lpx
import ru.dgis.sdk.positioning.DefaultLocationSource
import ru.dgis.sdk.positioning.registerPlatformLocationSource

class MapController(private val context: Context?, private val mapView: MapView?) :
    TouchEventsObserver {
    private var sdkContext = context?.let {
        DGis.initialize(
            appContext = it
        )
    }
    private var mapObjectManager: MapObjectManager? = null
    private var permissionState: Boolean = false
    private var selectedMarkerId: Int? = null
    var onPointClick: (Int) -> Unit = {
        selectedMarkerId = it
    }

    fun updatePointsCollection(points: List<PointMapModel>) {
        mapView!!.getMapAsync {
            mapObjectManager = MapObjectManager(it)
            mapView.setTouchEventsObserver(this)
            points.forEachIndexed { i, point ->
                val onClickCallback = { dgisMarker: Marker, customMarker: PointMapModel, getId: (Int) -> Unit ->
                    Toast
                        .makeText(
                            this.context,
                            "Marker #${customMarker.id} clicked",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                    getId(customMarker.id)
                }
                val listener = OnClickListener(
                    customMarker = PointMapModel(
                        i + 1,
                        latitude = point.latitude,
                        longitude = point.longitude,
                        visible = true
                    ),
                    onClick = onClickCallback
                )
                val options = MarkerOptions(
                    position = GeoPointWithElevation(
                        latitude = point.latitude,
                        longitude = point.longitude
                    ),
                    icon = sdkContext?.let { it1 -> imageFromResource(it1, R.drawable.marker) },
                    iconWidth = 35.lpx,
                    userData = listener
                )
                val marker = Marker(options)
                mapObjectManager!!.addObject(marker)
            }
        }
    }

    override fun onTap(point: ScreenPoint) {
        mapView!!.getMapAsync { map ->
            map.getRenderedObjects(point, ScreenDistance(5f)).onResult { renderedObjectInfos ->
                val mapObject = renderedObjectInfos.firstOrNull()?.item?.item ?: return@onResult
                val dgisMarker = mapObject as? Marker ?: return@onResult
                val listener = dgisMarker.userData as? OnClickListener ?: return@onResult

                listener.onClick(dgisMarker, listener.customMarker, onPointClick)
            }
        }
    }

    fun mapPointListener(selectedMarkerId: Int) {
        Log.i("", "currentMarkerId: $selectedMarkerId")
    }

    fun getMarkerId() = selectedMarkerId

    fun updateLocation(myLocation: LatLng) {
        checkLocationPermission()
        val locationSource = context?.let { DefaultLocationSource(context = it) }
        sdkContext?.let { registerPlatformLocationSource(it, locationSource) }
        var cameraPosition: CameraPosition
        val source = sdkContext?.let {
            MyLocationMapObjectSource(
                it,
                MyLocationController(BearingSource.MAGNETIC)
            )
        }
        mapView!!.getMapAsync { map ->
            Log.i("", "locator")
            cameraPosition = CameraPosition(
                point = GeoPoint(myLocation.lat, myLocation.lng),
                zoom = Zoom(16f),
            )
            if (source != null) {
                map.addSource(source)
            }
            map.camera.move(cameraPosition, Duration.ofSeconds(1), CameraAnimationType.DEFAULT)
        }
    }

    fun checkLocationPermission(): Boolean {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        if (context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    permission
                )
            } == PackageManager.PERMISSION_GRANTED
        ) {
            permissionState = true
        } else {
            permissionState = false
        }
        return permissionState
    }
}