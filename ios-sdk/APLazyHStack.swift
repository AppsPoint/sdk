import Foundation
import SwiftUI
import shared

struct APLazyHStack<Content>: View where Content: View {
    var alignment: Alignment = .topLeading
    var spacing: CGFloat = 0
    var contentPadding: EdgeInsets? = nil
    @ViewBuilder var content: () -> Content

    var body: some View {
        GeometryReader { scrollViewProxy in
            ScrollView(.horizontal, showsIndicators: false) {
                if [Alignment.topLeading, .leading, .bottomLeading].contains(alignment) {
                    LazyHStack(
                            alignment: [Alignment.topLeading, .top, .topTrailing].contains(alignment) ? .top
                                    : [Alignment.leading, .center, .trailing].contains(alignment) ? .center
                                    : [Alignment.bottomLeading, .bottom, .bottomTrailing].contains(alignment) ? .bottom
                                    : .top,
                            spacing: spacing,
                            content: content
                    )
                            .conditional(contentPadding != nil) { view in
                                view.padding(contentPadding!)
                            }
                } else {
                    APHStack(alignment: alignment, spacing: spacing, contentPadding: contentPadding, content: content)
                            .frame(width: scrollViewProxy.size.width, height: scrollViewProxy.size.height)
                }
            }
        }
    }

    @ViewBuilder func frame(maxWidth: CGFloat? = nil, width: CGFloat? = nil, maxHeight: CGFloat? = nil, height: CGFloat? = nil) -> some View {
        let preResult = frame(maxWidth: maxWidth, maxHeight: maxHeight, alignment: alignment)
        if width != nil || height != nil {
            preResult.frame(width: maxWidth == .infinity ? nil : width, height: maxHeight == .infinity ? nil : height, alignment: alignment ?? .topLeading)
        } else {
            preResult
        }
    }

    @ViewBuilder func frame(width: CGFloat? = nil, height: CGFloat? = nil) -> some View {
        frame(width: width, height: height, alignment: alignment)
    }
}
