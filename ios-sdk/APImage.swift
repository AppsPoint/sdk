import Foundation
import SwiftUI
import shared

struct APImage: View {

    var value: ImageSource?

    var body: some View {
        if value is RemoteImageSource {
            AsyncImage(url: URL(string: (value as! RemoteImageSource).url)!, placeholder: { EmptyView() }, image: { Image(uiImage: $0).resizable() })
        } else if value is LocalImageSource {
            Image((value as! LocalImageSource).name).resizable()
        } else {
            EmptyView()
        }
    }
}