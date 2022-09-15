import Foundation
import SwiftUI
import shared

struct APTextFieldStyles {
    var textStyle: APTextStyle = APTextStyle()
    var placeholderStyle: APTextStyle = APTextStyle()
}

struct APTextFieldColors {
    var textColor: Color? = nil
    var placeholderColor: Color? = nil
    var cursorColor: Color? = nil
}

struct APTextFieldWrapper<Content, ClearIcon>: View where Content: View, ClearIcon: View {

    @Binding var text: String
    var placeholder: String = ""
    var styles: APTextFieldStyles = APTextFieldStyles()
    var colors: APTextFieldColors = APTextFieldColors()
    @ViewBuilder var clearIcon: () -> ClearIcon
    @ViewBuilder var content: (() -> APTextField, () -> APClearIcon<ClearIcon>) -> Content

    init(
            text: Binding<String>,
            placeholder: String = "",
            styles: APTextFieldStyles = APTextFieldStyles(),
            colors: APTextFieldColors = APTextFieldColors(),
            content: @escaping (() -> APTextField, () -> APClearIcon<ClearIcon>) -> Content
    ) where ClearIcon == EmptyView {
        self._text = text
        self.placeholder = placeholder
        self.styles = styles
        self.colors = colors
        clearIcon = {
            EmptyView()
        }
        self.content = content
    }

    init(
            text: Binding<String>,
            placeholder: String = "",
            styles: APTextFieldStyles = APTextFieldStyles(),
            colors: APTextFieldColors = APTextFieldColors(),
            clearIcon: @escaping () -> ClearIcon,
            content: @escaping (() -> APTextField, () -> APClearIcon<ClearIcon>) -> Content
    ) {
        self._text = text
        self.placeholder = placeholder
        self.styles = styles
        self.colors = colors
        self.clearIcon = clearIcon
        self.content = content
    }

    var body: some View {
        content(
                { APTextField(text: $text, placeholder: placeholder, styles: styles, colors: colors) },
                { APClearIcon(text: $text, content: clearIcon) }
        )
    }
}

struct APTextField: View {
    @Binding var text: String
    @State var placeholder: String = ""
    var styles: APTextFieldStyles = APTextFieldStyles()
    var colors: APTextFieldColors = APTextFieldColors()

    var body: some View {
        ZStack(alignment: .topLeading) {
            TextEditor(text: $placeholder)
                    .disabled(true)
                    .conditional(styles.placeholderStyle.font != nil) { view in
                        view
                                .font(Font(styles.placeholderStyle.font!))
                                .conditional(styles.placeholderStyle.lineHeight != nil) { view in
                                    view
                                            .lineSpacing((styles.placeholderStyle.lineHeight! - styles.placeholderStyle.font!.lineHeight) / 2)
                                            .padding(.vertical, (styles.placeholderStyle.lineHeight! - styles.placeholderStyle.font!.lineHeight))
                                }
                    }
                    .conditional(colors.placeholderColor != nil) { view in
                        view.foregroundColor(colors.placeholderColor!)
                    }
                    .opacity(text.isEmpty ? 1 : 0)
            TextEditor(text: $text)
                    .conditional(styles.textStyle.font != nil) { view in
                        view
                                .font(Font(styles.textStyle.font!))
                                .conditional(styles.textStyle.lineHeight != nil) { view in
                                    view
                                            .lineSpacing((styles.textStyle.lineHeight! - styles.textStyle.font!.lineHeight) / 2)
                                            .padding(.vertical, (styles.textStyle.lineHeight! - styles.textStyle.font!.lineHeight))
                                }
                    }
                    .conditional(colors.textColor != nil) { view in
                        view.foregroundColor(colors.textColor!)
                    }
                    .conditional(colors.cursorColor != nil) { view in
                        view.accentColor(colors.cursorColor!)
                    }
        }
    }
}

struct APClearIcon<Content>: View where Content: View {
    @Binding var text: String

    @ViewBuilder var content: () -> Content

    var body: some View {
        if text.isEmpty {
            EmptyView()
        } else {
            content().onTapGesture {
                text = ""
            }
        }
    }
}