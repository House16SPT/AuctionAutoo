package com.example.auctionauto.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.auctionauto.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountInfoScreen(onPaymentInfoClick: () -> Unit,onBack: () -> Unit){

    val context = LocalContext.current

    var paymentMethods by remember {
        mutableStateOf(loadPaymentMethods(context))
    }
    // AccountInfo implementation
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.accountinfo),
                        contentDescription = "AuctionAuto Logo",
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
            ){
                item {
                    val name = "[FULL NAME]"  //CHANGE LATER TO GET FROM DATABASE (IF WE ADD NAMES)
                    val email = "test@email.com"  //CHANGE LATER TO GET FROM DATABASE
                    Text(name)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(email)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text("Saved payment methods: ")
                    Spacer(modifier = Modifier.height(10.dp))

                    if (paymentMethods.isEmpty()) {
                        Text("No payment methods saved.")
                    }
                }
                items(paymentMethods) { method ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(horizontal = 2.dp, vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        )
                        {
                            Text("Account: ${maskLast4(method.checking)}")

                            Spacer(modifier = Modifier.width(2.dp))

                            IconButton(onClick = {
                                deletePaymentMethod(context, paymentMethods.indexOf(method))
                                paymentMethods = loadPaymentMethods(context)
                            }) {
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
                item {
                    Spacer(modifier = Modifier.height(80.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ){
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

                    Spacer(modifier = Modifier.height(80.dp))

                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ){

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

fun maskLast4(number: String): String { //AI wrote this
    return number.takeLast(4).padStart(number.length, '*')
}
