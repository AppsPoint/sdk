package ru.apps_point.sdk.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import ru.apps_point.sdk.*
import ru.apps_point.sdk.demo.ui.theme.AppsPointsdkTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(32.dp)
            ) {
                BottomSheetScaffold(sheetContent = {}, sheetElevation = 0.dp) {

                }
                Row(
                    modifier = Modifier
                        .width(200.dp)
                        .height(100.dp)
                        .outerShadow(
                            color = Color.Gray.copy(alpha = 0.9f),
                            offsetX = 5.dp,
                            offsetY = 5.dp,
                            blurRadius = 15.dp,
                            shape = RoundedCornerShape(32.dp, 32.dp, 0.dp, 0.dp)
                        )

                        .clip(
                            shape = RoundedCornerShape(32.dp, 32.dp, 0.dp, 0.dp)
                        )
                        .background(
                            Color.White,
                        )
                ) {
                    Text(text = "Hi")
                }
            }

        }
    }
}
