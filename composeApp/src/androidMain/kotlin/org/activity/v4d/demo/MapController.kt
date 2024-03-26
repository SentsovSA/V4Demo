package org.activity.v4d.demo

import PointMapModel
import android.content.Context
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import ru.dgis.sdk.DGis
import ru.dgis.sdk.geometry.GeoPointWithElevation
import ru.dgis.sdk.map.MapObjectManager
import ru.dgis.sdk.map.MapView
import ru.dgis.sdk.map.Marker
import ru.dgis.sdk.map.MarkerOptions
import ru.dgis.sdk.map.RenderedObjectInfo
import ru.dgis.sdk.map.ScreenDistance
import ru.dgis.sdk.map.ScreenPoint
import ru.dgis.sdk.map.TouchEventsObserver
import ru.dgis.sdk.map.imageFromResource
import ru.dgis.sdk.map.lpx

class MapController(context: Context, private var mapView: MapView) : TouchEventsObserver {
    private var sdkContext = DGis.initialize(
        appContext = context
    )
    private var mapObjectManager: MapObjectManager? = null
    private var permissionState: Boolean = false
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    fun updatePointsCollection(points: List<PointMapModel>) {
        val markers = mutableListOf<Marker>()
        val markerOptions = mutableListOf<MarkerOptions>()
        mapView.getMapAsync {
            Log.i("", "zhopa1 ${points.size}")
            mapObjectManager = MapObjectManager(it)
            points.forEach { point ->
                markerOptions.add(
                    MarkerOptions(
                        position = GeoPointWithElevation(
                            latitude = point.latitude,
                            longitude = point.longitude
                        ),
                        icon = imageFromResource(sdkContext, R.drawable.marker),
                        iconWidth = 35.lpx
                    )
                )
            }
            markerOptions.forEach {
                markers.add(Marker(it))
            }
            mapObjectManager!!.addObjects(markers)
        }
    }

    fun onObjectTappedOrLongTouch(objInfo: RenderedObjectInfo) {
        Log.i("APP", "Произвольные данные объекта: ${objInfo.item.item.userData}")
    }

    override fun onTap(point: ScreenPoint) {
        mapView.getMapAsync { map ->
            map.getRenderedObjects(point, ScreenDistance(5f)).onResult { renderedObjectInfos ->
                for (renderedObjectInfo in renderedObjectInfos) {
                    Log.d(
                        "APP",
                        "Произвольные данные объекта: ${renderedObjectInfo.item.item.userData}"
                    )
                }
            }
        }
    }

    fun appPointListener(onPointClick: (id: Long) -> Unit) {
        mapView.addObjectTappedCallback { ::onObjectTappedOrLongTouch }
    }
}