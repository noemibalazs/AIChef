package com.noemi.aichef.screens.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.noemi.aichef.ui.theme.AIChefTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AIChefActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AIChefApp()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AIChefTheme {}
}