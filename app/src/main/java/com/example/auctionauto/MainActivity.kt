package com.example.auctionauto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.auctionauto.ui.theme.AuctionAutoTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AuctionAutoTheme {
                ScreenNavigator()  // this manages all the screens/gui stuff. we shouldn't need to edit MainActivity again!
            }
        }
    }
}
