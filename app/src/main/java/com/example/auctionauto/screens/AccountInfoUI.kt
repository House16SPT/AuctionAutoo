package com.example.auctionauto.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.auctionauto.R
import com.example.auctionauto.UserSession

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountInfoScreen(
    onPaymentInfoClick: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val email = UserSession.currentEmail ?: ""

    // Load only payment methods belonging to THIS user
    var paymentMethods by remember {
        mutableStateOf(
            loadPaymentMethods(context).filter { it.email == email }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.accountinfo),
                        contentDescription = "Account Info",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp)
                            .height(65.dp)
                            .width(275.dp),
                        contentScale = ContentScale.FillBounds
                    )
                },
                modifier = Modifier.height(100.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFB53A1D),
                    titleContentColor = Color.White
                )
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

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {

                // Header info
                item {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(email)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text("Saved payment methods:")
                    Spacer(modifier = Modifier.height(10.dp))

                    if (paymentMethods.isEmpty()) {
                        Text("No payment methods saved.")
                    }
                }

                // Payment cards
                items(paymentMethods) { method ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 2.dp, vertical = 2.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text("Account: ${maskLast4(method.checking)}")

                            IconButton(
                                onClick = {
                                    deletePaymentMethod(context, paymentMethods.indexOf(method))
                                    paymentMethods = loadPaymentMethods(context)
                                        .filter { it.email == email }
                                }
                            ) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "Delete",
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }

                // Bottom Buttons
                item {
                    Spacer(modifier = Modifier.height(80.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = onPaymentInfoClick,
                            modifier = Modifier
                                .width(300.dp)
                                .height(100.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFB53A1D)
                            )
                        ) {
                            Text("Add a Payment Method")
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = onBack,
                            modifier = Modifier
                                .width(300.dp)
                                .height(100.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFB53A1D)
                            )
                        ) {
                            Text("Back to Dashboard")
                        }
                    }
                }
            }
        }
    }
}

fun maskLast4(number: String): String {
    return number.takeLast(4).padStart(number.length, '*')
}
