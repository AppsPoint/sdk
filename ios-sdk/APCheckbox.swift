import Foundation
import SwiftUI
import shared

struct APCheckbox<UncheckedState, CheckedState, DisabledUncheckedState, DisabledCheckedState>: View where UncheckedState: View, CheckedState: View, DisabledUncheckedState: View, DisabledCheckedState: View {
    @Binding var checked: Bool
    var enabled = true
    @ViewBuilder var uncheckedState: () -> UncheckedState
    @ViewBuilder var checkedState: () -> CheckedState
    @ViewBuilder var disabledUncheckedState: () -> DisabledUncheckedState
    @ViewBuilder var disabledCheckedState: () -> DisabledCheckedState

    var body: some View {
        if !checked && enabled {
            uncheckedState().onTapGesture {
                self.checked.toggle()
            }
        } else if checked && enabled {
            checkedState().onTapGesture {
                self.checked.toggle()
            }
        } else if !checked {
            disabledUncheckedState().onTapGesture {
                self.checked.toggle()
            }
        } else {
            disabledCheckedState().onTapGesture {
                self.checked.toggle()
            }
        }
    }
}