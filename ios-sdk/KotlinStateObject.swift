import Foundation
import SwiftUI
import shared

class KotlinStateObject<T: KotlinObservableObject>: ObservableObject {

    var wrappedValue: T

    init(wrappedValue: T) {
        self.wrappedValue = wrappedValue
        KotlinObservableObjects.shared.observeAll { [weak self] in
            print("On change")
            self?.objectWillChange.send()
        }
    }
}