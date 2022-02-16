package ru.apps_point.sdk.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import ru.apps_point.sdk.StyledTextField
import ru.apps_point.sdk.demo.ui.theme.AppsPointsdkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppsPointsdkTheme {
                Surface(color = MaterialTheme.colors.background) {
                    var value by remember { mutableStateOf("") }
                    StyledTextField(value = value, onValueChange = { value = it }, error = "Fuck u")
                }
            }
        }
    }
}
