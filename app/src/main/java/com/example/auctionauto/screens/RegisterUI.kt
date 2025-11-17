package com.example.auctionauto.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.auctionauto.R
import com.example.auctionauto.data.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RegisterScreen(
    onBack: () -> Unit,
    userRepo: UserRepository
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") } // optional: you'll need a name field; if not present, it will be empty
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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

        // Email/password fields placed as before (kept layout)
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
        TextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Re-type Password") },
            modifier = Modifier.offset(y = 150.dp)
        )
        Button(
            onClick = {
                // validate & register
                if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                    Toast.makeText(context, "All fields required", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                if (password != confirmPassword) {
                    Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                coroutineScope.launch {
                    val created = withContext(Dispatchers.IO) {
                        userRepo.registerUser(name.trim(), email.trim(), password)
                    }
                    if (created) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Account created!", Toast.LENGTH_SHORT).show()
                            onBack()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Email already registered", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            modifier = Modifier
                .width(300.dp)
                .height(50.dp)
                .offset(y = 225.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFB53A1D)
            )
        ) {
            Text("Create Account")
        }
        Button(
            onClick = onBack,
            modifier = Modifier
                .width(300.dp)
                .height(50.dp)
                .offset(y = 290.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFB53A1D)
            )
        ) {
            Text("Back to Login")
        }
    }
}
