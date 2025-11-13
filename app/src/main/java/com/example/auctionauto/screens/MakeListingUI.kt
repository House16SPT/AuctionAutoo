package com.example.auctionauto.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakeListingScreen(onBack: () -> Unit) {
    var make by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    // MakeListing implementation
    Scaffold (
        topBar = {
            TopAppBar(
                title = {Text("Make Listing")}
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(bottom = 50.dp)
                .padding(top = 50.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                 Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Center
                ) {
                     TextField(
                         modifier = Modifier.width(125.dp),
                         value = make,
                         onValueChange = { make = it },
                         label = { Text("Make") },
                     )
                    Spacer(Modifier.width(10.dp))
                     TextField(
                         modifier = Modifier.width(125.dp),
                         value = model,
                         onValueChange = { model = it },
                         label = { Text("Model") },
                     )
                }
                Spacer(Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(
                        modifier = Modifier.width(125.dp),
                        value = color,
                        onValueChange = { color = it },
                        label = { Text("Color") },
                    )
                    Spacer(Modifier.width(10.dp))
                    TextField(
                        modifier = Modifier.width(125.dp),
                        value = year,
                        onValueChange = { year = it },
                        label = { Text("Year") },
                    )
                }
            }
            Button(
                onClick = {},
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB53A1D)
                )
            ) {
                Text("Post Listing")
            }
        }
    }
}