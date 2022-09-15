import Foundation
import SwiftUI
import shared

protocol ViewIntegration {
}

struct ViewIntegrationContext<VM: ViewModel> {
    var viewModel: VM
    var listItems: [Any?] = []
}