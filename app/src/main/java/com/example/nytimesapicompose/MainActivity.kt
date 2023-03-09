package com.example.nytimesapicompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.nytimesapicompose.navigation.BookNavigation
import com.example.nytimesapicompose.ui.theme.NyTimesApiComposeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NyTimesApiComposeTheme{
            BookNavigation()
            }
        }
    }
}

