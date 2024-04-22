package org.activity.v4d.demo

import PointMapModel
import ru.dgis.sdk.map.Marker

class OnClickListener(
    val customMarker: PointMapModel,
    val onClick: (Marker, PointMapModel, (id: Int) -> Unit) -> Unit
)