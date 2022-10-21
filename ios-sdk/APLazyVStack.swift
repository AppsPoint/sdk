import Foundation
import SwiftUI
import shared


struct APLazyVStack<Content>: View where Content: View {
    var alignment: Alignment = .topLeading
    var spacing: CGFloat = 0
    var contentPadding: EdgeInsets? = nil
    @ViewBuilder var content: () -> Content

    var body: some View {
        GeometryReader { scrollViewProxy in
            ScrollView(showsIndicators: false) {
                if [Alignment.topLeading, .leading, .bottomLeading].contains(alignment) {
                    LazyVStack(
                            alignment: [Alignment.topLeading, .leading, .bottomLeading].contains(alignment) ? .leading
                                    : [Alignment.top, .center, .bottom].contains(alignment) ? .center
                                    : [Alignment.topTrailing, .trailing, .bottomTrailing].contains(alignment) ? .trailing
                                    : .leading,
                            spacing: spacing,
                            content: content
                    )
                            .conditional(contentPadding != nil) { view in
                                view.padding(contentPadding!)
                            }
                } else {
                    APVStack(alignment: alignment, spacing: spacing, contentPadding: contentPadding, content: content)
                            .frame(width: scrollViewProxy.size.width, height: scrollViewProxy.size.height)
                }
            }
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