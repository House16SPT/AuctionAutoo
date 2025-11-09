package com.example.auctionauto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.unit.sp
import com.example.auctionauto.ui.theme.AuctionAutoTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding


class Dashboard : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val choices = listOf("Make a listing", "My listings", "My bids", "Account info")
            var expanded by remember { mutableStateOf(false) }

            AuctionAutoTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Box {
                                    Image(
                                        painter = painterResource(id = R.drawable.auctionauto),
                                        contentDescription = "AuctionAuto Logo",
                                        modifier = Modifier
                                            .height(65.dp)
                                            .width(275.dp),
                                        contentScale = ContentScale.FillBounds
                                    )

                                    Button(
                                        onClick = { expanded = true },
                                        modifier = Modifier
                                            .width(80.dp)
                                            .height(70.dp)
                                            .offset(x=300.dp)
                                            .offset(y=0.dp),
                                        shape = RoundedCornerShape(0.dp),
                                        contentPadding = PaddingValues(0.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.auctionautosquarebutton),
                                            contentDescription = "Menu Button",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.FillBounds
                                        )
                                    }

                                    DropdownMenu(
                                        expanded = expanded,
                                        onDismissRequest = { expanded = false },
                                        modifier = Modifier.align(Alignment.TopEnd)
                                    ) {
                                        choices.forEach { choice ->
                                            DropdownMenuItem(
                                                text = { Text(choice) },
                                                onClick = {
                                                    expanded = false
                                                    println("Selected: $choice")
                                                }
                                            )
                                        }
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(0xFFB53A1D),
                                titleContentColor = Color.White
                            )
                        )
                    },
                    content = { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Welcome to the Dashboard!", fontSize = 24.sp)
                        }
                    }
                )
            }
        }
    }
}