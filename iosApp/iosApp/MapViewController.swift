//
//  MapViewController.swift
//  iosApp
//
//  Created by SentsovSA on 22.03.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import ComposeApp
import UIKit
import DGis

class MapViewController: UIViewController {
    private let marker: UIImage = UIImage(named: "marker")!
    private var dataLoadingStateCancellable: ICancellable = NoopCancellable()
    lazy var sdk = DGis.Container()
    var map: Map?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        do {
            let apiKeyOptions = ApiKeyOptions(apiKeyFile: File(path: "/Users/user/V4Demo/composeApp/src/androidMain/assets/dgissdk.key"))
            sdk = DGis.Container(apiKeyOptions: apiKeyOptions)
            var mapOptions = MapOptions.default
            mapOptions.devicePPI = .autodetected
            let mapFactory = try sdk.makeMapFactory(options: mapOptions)
            let mapView: UIView & IMapView = mapFactory.mapView
            mapView.frame = self.view.bounds
            let map = mapFactory.map
            self.view.addSubview(mapView)
            
            self.dataLoadingStateCancellable = mapFactory.map.dataLoadingStateChannel.sink { loadingState in
                if loadingState == .loaded {
                    print("Now map is loaded")
                }
            }
        } catch let error as SDKError {
            print(error.description)
        } catch {
            print("System error: \(error)")
            map = nil
        }
    }
    
    private func move(to cameraPosition: CameraPosition) {
        map?.camera.move(position: cameraPosition, time: 2, animationType: .default)
    }
    
    private func addMyLocationPlacemark() {
       
        }
        
        private func addCustomLocationPlacemark() {
           
        }

        private var mapView: IMapView!
        private var myPointPlacemark: Marker!
        private var customPointPlacemark: Marker!
        
        private var pinsCollection: Array<Marker>!

        private lazy var connection: Cancellable? = Cancellable()
    private lazy var mapObjectTappedOrLongPress: MapObjectTappedCallback = MapObjectTappedCallback { objectInfo in
        //TODO
    }
        
        func addCameraListener(onDragged: @escaping () -> Void) {
            connection = map?.camera.positionChannel.sink { position in
                
            }
        }
        
        func addPointListener(onPointClick: @escaping (KotlinLong) -> Void) {
            mapObjectTappedOrLongPress = MapObjectTappedCallback(callback: { [weak self] objectInfo in
            })
        }
        
        func mapMove(latLng: LatLng) {
            map?.camera.move(position: CameraPosition(point: GeoPoint(latitude: latLng.lat, longitude: latLng.lng), zoom: 16.0), time: 0.4, animationType: .default)
        }
        
        func updateMyPoint(latLng: LatLng?, visible: Bool) {
            
        }
        
        func updateCustomPoint(latLng: LatLng?, visible: Bool) {
           
        }
        
        func updateFocusArea(bottomFocusAreaPadding: Int) {
           
        }
        
        func updatePointsCollection(points: [PointMapModel]) {
            
        }
}
