package com.keepsake.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.keepsake.app.ui.KeepsakeNavGraph
import com.keepsake.app.ui.theme.KeepsakeTheme
import com.keepsake.app.ui.theme.Parchment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KeepsakeTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Parchment) {
                    KeepsakeNavGraph()
                }
            }
        }
    }
}
