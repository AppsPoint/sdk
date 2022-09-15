import Foundation
import SwiftUI
import shared

struct APTextStyle {
    var font: UIFont? = nil
    var underline: Bool = false
    var strikethrough: Bool = false
    var lineHeight: CGFloat? = nil
}

struct APText: View {
    var text: String
    var style: APTextStyle = APTextStyle()
    var color: Color? = nil
    var alignment: TextAlignment? = nil

    var body: some View {
        Text(text)
                .conditional(style.font != nil) { view in
                    view
                            .font(Font(style.font!))
                            .conditional(style.lineHeight != nil) { view in
                                view
                                        .lineSpacing((style.lineHeight! - style.font!.lineHeight) / 2)
                                        .padding(.vertical, (style.lineHeight! - style.font!.lineHeight))
                            }
                }
                .conditional(color != nil) { view in
                    view.foregroundColor(color!)
                }
                .conditional(style.underline) { view in
                    (view as! Text).underline()
                }
                .conditional(style.strikethrough) { view in
                    (view as! Text).strikethrough()
                }
                .conditional(alignment != nil) { view in
                    view.multilineTextAlignment(alignment!)
                }
    }

    private var frameAlignment: Alignment {
        switch alignment {
        case .leading:
            return Alignment.leading
        case .center:
            return Alignment.center
        case .trailing:
            return Alignment.trailing
        default:
            return Alignment.leading
        }
    }

    @ViewBuilder func frame(maxWidth: CGFloat? = nil, maxHeight: CGFloat? = nil, width: CGFloat? = nil, height: CGFloat? = nil) -> some View {
        let preResult = frame(maxWidth: maxWidth, maxHeight: maxHeight, alignment: frameAlignment)
        if width != nil || height != nil {
            preResult.frame(width: maxWidth == .infinity ? nil : width, height: maxHeight == .infinity ? nil : height, alignment: frameAlignment)
        } else {
            preResult
        }
    }

    @ViewBuilder func frame(width: CGFloat? = nil, height: CGFloat? = nil) -> some View {
        frame(width: width, height: height, alignment: frameAlignment)
    }
}
