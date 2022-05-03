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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import ru.apps_point.sdk.CustomCheckbox
import ru.apps_point.sdk.EnhancedTextField
import ru.apps_point.sdk.EnhancedTextFieldDefaults
import ru.apps_point.sdk.demo.ui.theme.AppsPointsdkTheme
import ru.apps_point.sdk.rememberSvgPainter

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppsPointsdkTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Box(modifier = Modifier.padding(16.dp)) {
                        var c by remember {
                            mutableStateOf(false)
                        }
                        CustomCheckbox(
                            checked = c,
                            onCheckedChange = { c = !c },
                            uncheckedState = {
                                Image(
                                    painter = rememberSvgPainter(url = "file:///android_asset/unchecked.svg"),
                                    contentDescription = null
                                )
                            },
                            checkedState = {
                                Image(
                                    painter = rememberSvgPainter(url = "file:///android_asset/checked.svg"),
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}
