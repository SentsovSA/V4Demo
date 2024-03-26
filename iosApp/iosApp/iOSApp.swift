import SwiftUI
import DGis
import ComposeApp

@main
struct iOSApp: App {
    
    init() {
        Koin_iosKt.doInitKoinIos(mapProtocol: DGisProtocolImpl())
    }

    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
