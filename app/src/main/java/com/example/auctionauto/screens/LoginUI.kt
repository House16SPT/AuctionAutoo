package com.example.auctionauto.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.auctionauto.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement

@Composable
fun LoginScreen(modifier: Modifier = Modifier, onRegisterClick: () -> Unit, onLoginClick: () -> Unit) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Scaffold(
    ) { innerPadding ->
        Box(
            modifier = modifier.fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.auctionauto),
                    contentDescription = "AuctionAuto Logo",
                    modifier = Modifier
                        .offset(y=-100.dp)
                        .width(400.dp)
                        .height(90.dp),
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    "Welcome to Auction Auto", modifier = Modifier,
                    fontSize = 20.sp
                )
                Spacer(modifier= Modifier.height(16.dp))
                Text(
                    "Please Login", modifier = Modifier,
                    fontSize = 20.sp
                )
                Spacer(modifier= Modifier.height(16.dp))
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") }
                )
                Spacer(modifier= Modifier.height(16.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                )
                Spacer(modifier= Modifier.height(16.dp))
                Button(
                    onClick = { onLoginClick() },
                    modifier = Modifier
                        .width(300.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB53A1D)
                    )
                ) {
                    Text("Login")
                }
                Spacer(modifier= Modifier.height(16.dp))
                Button(
                    onClick = onRegisterClick,
                    modifier = Modifier
                        .width(300.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB53A1D)
                    )
                ) {
                    Text("Register")
                }
            }
        }
    }
}