package com.example.auctionauto.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
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
import androidx.compose.foundation.layout.Arrangement
import com.example.auctionauto.data.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    userRepo: UserRepository
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Scaffold { innerPadding ->
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
                    onClick = {
                        // check login via Room
                        coroutineScope.launch {
                            val ok = withContext(Dispatchers.IO) {
                                userRepo.loginUser(email.trim(), password)
                            }
                            if (ok) {
                                Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                                onLoginClick()
                            } else {
                                Toast.makeText(context, "Invalid email or password", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
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
