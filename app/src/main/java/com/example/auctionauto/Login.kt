package com.example.auctionauto

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.auctionauto.ui.theme.AuctionAutoTheme


class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var Email = ""
        var Password = ""

        enableEdgeToEdge()
        setContent {
            AuctionAutoTheme {
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ){
                        Image(
                            painter = painterResource(id=R.drawable.auctionauto),
                            contentDescription = "AuctionAuto Logo",
                            modifier = Modifier
                                .offset(y=-175.dp)
                                .padding(innerPadding)
                                .width(400.dp)
                                .height(90.dp),
                            contentScale = ContentScale.FillBounds
                        )
                        Text("Welcome to Auction Auto", modifier = Modifier.align(Alignment.Center)
                            .padding(innerPadding)
                            .offset(y= -100.dp),
                            fontSize = 20.sp
                        )
                        Text("Please Login", modifier = Modifier
                            .padding(innerPadding)
                            .offset(y=-75.dp),
                            fontSize = 20.sp
                        )
                        TextField(
                            value = Email,
                            onValueChange = {Email = it},
                            label = { Text("Email") }
                        )
                        TextField(
                            value = Password,
                            onValueChange = {Password = it},
                            label = { Text("Password") },
                            modifier = Modifier.padding(innerPadding)
                                .offset(y=75.dp)
                        )
                        Button(onClick = {
                            val intent = Intent(this@Login, Dashboard::class.java)
                            startActivity(intent)
                        }, modifier = Modifier
                            .padding(innerPadding)
                            .width(300.dp)
                            .height(50.dp)
                            .offset(y=160.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFB53A1D)
                            )

                        )
                        {
                            Text("Login")

                        }
                        Button(onClick = {register()},
                            modifier = Modifier
                            .padding(innerPadding)
                            .width(300.dp)
                            .height(50.dp)
                            .offset(y=225.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFB53A1D)
                            )
                        )
                        {
                            Text("Register")

                        }
                    }

                }
            }
        }

    fun register()
    {
        var email = "";
        var password = "";
        enableEdgeToEdge()
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id=R.drawable.auctionauto),
                        contentDescription = "AuctionAuto Logo",
                        modifier = Modifier
                            .offset(y=-175.dp)
                            .padding(innerPadding)
                            .width(400.dp)
                            .height(90.dp),
                        contentScale = ContentScale.FillBounds
                    )
                    Text("Welcome to Auction Auto", modifier = Modifier.align(Alignment.Center)
                        .padding(innerPadding)
                        .offset(y= -100.dp),
                        fontSize = 20.sp
                    )
                    Text("Please enter your information for registration", modifier = Modifier
                        .padding(innerPadding)
                        .offset(y=-75.dp),
                        fontSize = 20.sp
                    )
                    TextField(
                        value = email,
                        onValueChange = {email = it},
                        label = { Text("Email") }
                    )
                    TextField(
                        value = password,
                        onValueChange = {password = it},
                        label = { Text("Password") },
                        modifier = Modifier.padding(innerPadding)
                            .offset(y=75.dp)
                    )
                    TextField(
                        value = password,
                        onValueChange = {password = it},
                        label = { Text("Re-type Password") },
                        modifier = Modifier.padding(innerPadding)
                            .offset(y=150.dp)
                    )
                    Button(onClick = {register()},
                        modifier = Modifier
                            .padding(innerPadding)
                            .width(300.dp)
                            .height(50.dp)
                            .offset(y=225.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFB53A1D)
                        )
                    )
                    {
                        Text("Create Account")

                    }
                }
            }
        }
    }
}

