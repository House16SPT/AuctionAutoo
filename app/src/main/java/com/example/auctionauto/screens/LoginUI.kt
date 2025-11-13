package com.example.auctionauto.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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

@Composable
fun LoginScreen(modifier: Modifier = Modifier, onRegisterClick: () -> Unit, onLoginClick: () -> Unit) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.auctionauto),
            contentDescription = "AuctionAuto Logo",
            modifier = Modifier
                .offset(y = -175.dp)
                .width(400.dp)
                .height(90.dp),
            contentScale = ContentScale.FillBounds
        )
        Text(
            "Welcome to Auction Auto", modifier = Modifier
                .align(Alignment.Center)
                .offset(y = -100.dp),
            fontSize = 20.sp
        )
        Text(
            "Please Login", modifier = Modifier
                .offset(y = -75.dp),
            fontSize = 20.sp
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.offset(y = 75.dp)
        )
        Button(
            onClick = { onLoginClick() },
            modifier = Modifier
                .width(300.dp)
                .height(50.dp)
                .offset(y = 160.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFB53A1D)
            )
        ) {
            Text("Login")
        }
        Button(
            onClick = onRegisterClick,
            modifier = Modifier
                .width(300.dp)
                .height(50.dp)
                .offset(y = 225.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFB53A1D)
            )
        ) {
            Text("Register")
        }
    }
}