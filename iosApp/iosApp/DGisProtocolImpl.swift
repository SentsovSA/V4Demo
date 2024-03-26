//
//  DGisMapProtocolImpl.swift
//  iosApp
//
//  Created by SentsovSA on 22.03.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import ComposeApp
import UIKit

class DGisProtocolImpl: DGisProtocol {
    var mapViewController: MapViewController
    var viewController: UIViewController
        
    init() {
        self.mapViewController = MapViewController()
        self.viewController = (self.mapViewController as UIViewController)
    }
    
    func addCameraListener(onDragged: @escaping () -> Void) {
        mapViewController.addCameraListener(onDragged: onDragged)
    }
    
    func addMapListener(onPositionSelect: @escaping (KotlinDouble, KotlinDouble) -> Void) {
        
    }
    
    func onMapMove(latLng: LatLng) {
        mapViewController.mapMove(latLng: latLng)
    }
    
    func onMapStart() {
        
    }
    
    func onMapStop() {
        
    }
    
    func setupFocusRect(bottomFocusAreaPadding: Int32) {
        
    }
    
    func updateCustomPoint(latLng: LatLng?, visible: Bool) {
        
    }
    
    func updateMyPoint(latLng: LatLng?, visible: Bool) {
        
    }
    
    func updatePointsCollection(points: [PointMapModel]) {
        
    }
    
    
}
