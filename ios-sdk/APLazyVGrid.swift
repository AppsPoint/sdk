import Foundation
import SwiftUI
import shared

enum GridCells {
    case adaptive
    case fixed(count: Int)
}

struct APLazyVGrid<Content>: View where Content: View {
    var columns: GridCells
    var alignment: Alignment? = nil
    var verticalSpacing: CGFloat = 0
    var horizontalSpacing: CGFloat = 0
    var contentPadding: EdgeInsets? = nil
    @ViewBuilder var content: () -> Content
    var gridItems: [GridItem] {
        switch columns {
        case .adaptive:
            return [GridItem(.adaptive(minimum: 10), spacing: horizontalSpacing)]
        case .fixed(count: let count):
            return [GridItem](repeating: GridItem(.flexible(), spacing: horizontalSpacing), count: count)
        }
    }

    var body: some View {

        ScrollView(showsIndicators: false) {
            LazyVGrid(
                    columns: gridItems,
                    alignment: [Alignment.topLeading, .leading, .bottomLeading].contains(alignment) ? .leading
                            : [Alignment.top, .center, .bottom].contains(alignment) ? .center
                            : [Alignment.topTrailing, .trailing, .bottomTrailing].contains(alignment) ? .trailing
                            : .leading,
                    spacing: verticalSpacing,
                    content: content
            )
                    .conditional(contentPadding != nil) { view in
                        view.padding(contentPadding!)
                    }
        }
    }

    @ViewBuilder func frame(maxWidth: CGFloat? = nil, maxHeight: CGFloat? = nil, width: CGFloat? = nil, height: CGFloat? = nil) -> some View {
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