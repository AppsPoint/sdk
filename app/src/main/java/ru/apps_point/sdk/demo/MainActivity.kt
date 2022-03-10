package ru.apps_point.sdk.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import ru.apps_point.sdk.EnhancedTextField
import ru.apps_point.sdk.demo.ui.theme.AppsPointsdkTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppsPointsdkTheme {
                Surface(color = MaterialTheme.colors.background) {
                    var value by remember { mutableStateOf("") }
                    EnhancedTextField(
                        value = value,
                        onValueChange = { value = it },
                        label = "labl",
                        leadingIcon = {
                            Icon(
                                painter = rememberVectorPainter(image = Icons.Filled.Info),
                                contentDescription = ""
                            )
                        },
                    )
                }
            }
        }
    }
}
