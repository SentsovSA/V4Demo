package di

import LatLng
import PointMapModel
import platform.UIKit.UIViewController

interface DGisProtocol {

    val viewController: UIViewController

    fun addCameraListener(onDragged: () -> Unit)

    fun addMapListener(onPositionSelect: (latitude: Double, longitude: Double) -> Unit)

    fun onMapMove(latLng: LatLng)

    fun updateCustomPoint(latLng: LatLng? = null, visible: Boolean = true)

    fun updateMyPoint(latLng: LatLng? = null, visible: Boolean = true)

    fun updatePointsCollection(points: List<PointMapModel>)

    fun setupFocusRect(bottomFocusAreaPadding: Int)

}