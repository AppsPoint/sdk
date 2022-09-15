import Foundation
import SwiftUI
import shared

extension UIScreen {
    static let width = UIScreen.main.bounds.size.width
    static let height = UIScreen.main.bounds.size.height
    static let size = UIScreen.main.bounds.size
}

extension View {
    @ViewBuilder func conditional<Content: View>(_ condition: Bool, _ transform: (Self) -> Content) -> some View {
        if condition {
            transform(self)
        } else {
            self
        }
    }

    @ViewBuilder func frame(maxWidth: CGFloat? = nil, maxHeight: CGFloat? = nil, width: CGFloat? = nil, height: CGFloat? = nil) -> some View {
        let preResult = frame(maxWidth: maxWidth, maxHeight: maxHeight, alignment: .topLeading)
        if width != nil || height != nil {
            preResult.frame(width: width, height: height, alignment: .topLeading)
        } else {
            preResult
        }
    }

    @ViewBuilder func frame(width: CGFloat? = nil, height: CGFloat? = nil) -> some View {
        frame(width: width, height: height, alignment: .topLeading)
    }

    @ViewBuilder func border<S: Shape>(strokeColor: Color, strokeWidth: CGFloat, shape: S) -> some View {
        overlay(shape.stroke(strokeColor, style: StrokeStyle(lineWidth: strokeWidth)))
    }

    @ViewBuilder func border(strokeColor: Color, strokeWidth: CGFloat) -> some View {
        border(strokeColor, width: strokeWidth)
    }
}

func findActualFontName(for shortName: String) -> String? {
    for familyName in UIFont.familyNames {
        for fontName in UIFont.fontNames(forFamilyName: familyName) {
            if try! NSRegularExpression(pattern: "[^a-zA-Z0-9]").stringByReplacingMatches(in: fontName, range: NSRange(location: 0, length: fontName.count), withTemplate: "").lowercased() == shortName {
                return fontName
            }
        }
        print(UIFont.fontNames(forFamilyName: familyName))
    }
    return nil
}