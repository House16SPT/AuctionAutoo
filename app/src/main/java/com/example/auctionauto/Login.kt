package com.example.auctionauto

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auctionauto.ui.theme.AuctionAutoTheme

class Login : ComponentActivity() {
    private lateinit var userRepo: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userRepo = UserRepository(this)
        enableEdgeToEdge()

        setContent {
            AuctionAutoTheme {
                LoginScreen(
                    onLoginSuccess = {
                        startActivity(Intent(this@Login, Dashboard::class.java))
                    },
                    onRegisterClick = {
                        register()
                    },
                    userRepo = userRepo
                )
            }
        }
    }

    @Composable
    fun LoginScreen(onLoginSuccess: () -> Unit, onRegisterClick: () -> Unit, userRepo: UserRepository) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
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
                    "Welcome to Auction Auto",
                    modifier = Modifier.offset(y = -100.dp),
                    fontSize = 20.sp
                )
                Text(
                    "Please Login",
                    modifier = Modifier.offset(y = -75.dp),
                    fontSize = 20.sp
                )
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.offset(y = -10.dp)
                )
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.offset(y = 65.dp)
                )
                Button(
                    onClick = {
                        if (userRepo.loginUser(email, password)) {
                            Toast.makeText(this@Login, "Login successful!", Toast.LENGTH_SHORT).show()
                            onLoginSuccess()
                        } else {
                            Toast.makeText(this@Login, "Invalid email or password", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .width(300.dp)
                        .height(50.dp)
                        .offset(y = 150.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB53A1D))
                ) {
                    Text("Login")
                }
                Button(
                    onClick = { onRegisterClick() },
                    modifier = Modifier
                        .width(300.dp)
                        .height(50.dp)
                        .offset(y = 220.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB53A1D))
                ) {
                    Text("Register")
                }
            }
        }
    }

    // Registration screen
    fun register() {
        var name = ""
        var email = ""
        var password = ""
        var confirm = ""

        enableEdgeToEdge()
        setContent {
            AuctionAutoTheme {
                var nameState by remember { mutableStateOf("") }
                var emailState by remember { mutableStateOf("") }
                var passwordState by remember { mutableStateOf("") }
                var confirmState by remember { mutableStateOf("") }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
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
                            "Welcome to Auction Auto",
                            modifier = Modifier.offset(y = -100.dp),
                            fontSize = 20.sp
                        )
                        Text(
                            "Please enter your information for registration",
                            modifier = Modifier.offset(y = -75.dp),
                            fontSize = 16.sp
                        )
                        TextField(
                            value = nameState,
                            onValueChange = { nameState = it },
                            label = { Text("Full Name") },
                            modifier = Modifier.offset(y = -20.dp)
                        )
                        TextField(
                            value = emailState,
                            onValueChange = { emailState = it },
                            label = { Text("Email") },
                            modifier = Modifier.offset(y = 50.dp)
                        )
                        TextField(
                            value = passwordState,
                            onValueChange = { passwordState = it },
                            label = { Text("Password") },
                            modifier = Modifier.offset(y = 120.dp)
                        )
                        TextField(
                            value = confirmState,
                            onValueChange = { confirmState = it },
                            label = { Text("Confirm Password") },
                            modifier = Modifier.offset(y = 190.dp)
                        )
                        Button(
                            onClick = {
                                when {
                                    nameState.isEmpty() || emailState.isEmpty() || passwordState.isEmpty() || confirmState.isEmpty() ->
                                        Toast.makeText(this@Login, "All fields required", Toast.LENGTH_SHORT).show()
                                    passwordState != confirmState ->
                                        Toast.makeText(this@Login, "Passwords do not match", Toast.LENGTH_SHORT).show()
                                    userRepo.checkEmailExists(emailState) ->
                                        Toast.makeText(this@Login, "Email already registered", Toast.LENGTH_SHORT).show()
                                    else -> {
                                        if (userRepo.registerUser(nameState, emailState, passwordState)) {
                                            Toast.makeText(this@Login, "Account created!", Toast.LENGTH_SHORT).show()
                                            recreate() // Reload Login screen
                                        } else {
                                            Toast.makeText(this@Login, "Error creating account", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .width(300.dp)
                                .height(50.dp)
                                .offset(y = 270.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB53A1D))
                        ) {
                            Text("Create Account")
                        }
                    }
                }
            }
        }
    }
}
