package org.activity.v4d.demo

import androidx.compose.ui.platform.LocalContext
import ru.dgis.sdk.Context
import ru.dgis.sdk.DGis
import ru.dgis.sdk.map.MapView

fun getMapView(context: android.content.Context): MapView {
    return MapView(context)
}

fun getSDKContext(context: android.content.Context): Context {
    val sdkContext = DGis.initialize(
        appContext = context
    )
    return sdkContext
}