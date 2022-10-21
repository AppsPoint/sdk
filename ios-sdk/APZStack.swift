import Foundation
import SwiftUI
import shared

struct APZStack<Content>: View where Content: View {
    var alignment: Alignment? = nil
    var contentPadding: EdgeInsets? = nil
    @ViewBuilder var content: () -> Content

    var body: some View {
        ZStack(
                alignment: alignment ?? .topLeading,
                content: content
        )
                .conditional(contentPadding != nil) { view in
                    view.padding(contentPadding!)
                }
    }

    @ViewBuilder func frame(maxWidth: CGFloat? = nil, width: CGFloat? = nil, maxHeight: CGFloat? = nil, height: CGFloat? = nil) -> some View {
        let preResult = frame(maxWidth: maxWidth, maxHeight: maxHeight, alignment: alignment ?? .topLeading)
        if width != nil || height != nil {
            preResult.frame(width: maxWidth == .infinity ? nil : width, height: maxHeight == .infinity ? nil : height, alignment: alignment ?? .topLeading)
        } else {
            preResult
        }
    }

    @ViewBuilder func frame(width: CGFloat? = nil, height: CGFloat? = nil) -> some View {
        frame(width: width, height: height, alignment: alignment ?? .topLeading)
    }
}
