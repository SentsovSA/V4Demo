import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.lighthousegames.logging.logging


@OptIn(ExperimentalMaterialApi::class)
@Composable
@Preview
fun App() {
    BottomSheetNavigator {
        var id by remember { mutableStateOf(getSelectedId()) }
        val bottomSheetNavigator = LocalBottomSheetNavigator.current
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                DGisMap(
                    modifier = Modifier.fillMaxSize(),
                    enabled = true,
                    zoom = 15f,
                    location = LatLng(55.759616, 37.641530),
                    startPosition = LatLng(55.759616, 37.641530),
                    selectedMarkerId = id,
                    points = listOf(
                        PointMapModel(
                            id = 1,
                            latitude = 55.752425,
                            longitude = 37.613983,
                            visible = true
                        ),
                        PointMapModel(
                            id = 2,
                            latitude = 55.729065,
                            longitude = 37.620507,
                            visible = true
                        ),
                        PointMapModel(
                            id = 3,
                            latitude = 55.709065,
                            longitude = 37.610507,
                            visible = true
                        ),
                    ),
                    bottomFocusAreaPadding = 0,
                    onPointClick = {
                        id = it
                    },
                    onDragged = {}
                )
                LaunchedEffect(id) {
                    if (id != null && id != -1) {
                        bottomSheetNavigator.show(InfoScreen(id = id!!))
                        id = -1
                    }
                }
                Button(
                    onClick = {
                        id = getSelectedId()
                        logging().i { "clicked${id}" }
                        if (id != null) {

                        }
                    }
                ) {

                }
            }
        }
    }
}
