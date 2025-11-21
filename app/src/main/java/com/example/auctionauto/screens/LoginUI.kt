package com.example.auctionauto.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auctionauto.R
import com.example.auctionauto.UserSession
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
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

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
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
                )
                Spacer(modifier= Modifier.height(16.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
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
                                UserSession.currentEmail = email
                                onLoginClick() // go to dashboard, handled by screen navigator
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
