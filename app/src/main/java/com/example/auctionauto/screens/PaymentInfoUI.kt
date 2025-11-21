package com.example.auctionauto.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.auctionauto.R
import com.example.auctionauto.ensureNumeric
import com.google.gson.Gson
import java.io.File

data class PaymentMethod(
    val checking: String,
    val account: String,
    val routing: String,
    val address: String,
    val state: String,
    val zip: String,
    val name: String
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentInfoScreen(onBack: () -> Unit){

    var checkingNumber by remember { mutableStateOf("") } // (starting price)
    var accountNumber by remember { mutableStateOf("") } // duration of auction
    var routingNumber by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var zip by remember { mutableStateOf("") }

    var checkingError by remember { mutableStateOf(false) }
    var accountError by remember { mutableStateOf(false) }
    var routingError by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val checkingNumberFocusRequester = remember { FocusRequester() }
    val accountNumberFocusRequester = remember { FocusRequester() }
    val routingNumberFocusRequester = remember { FocusRequester() }
    val addressFocusRequester = remember { FocusRequester() }
    val zipFocusRequester = remember { FocusRequester() }
    val stateFocusRequester = remember { FocusRequester() }
    val nameFocusRequester = remember { FocusRequester() }

    // AccountInfo implementation
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.paymentmethod),
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
            ) {
                item {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextField(
                            modifier = Modifier.width(250.dp).focusRequester(checkingNumberFocusRequester),
                            value = checkingNumber,
                            onValueChange = { checkingNumber = ensureNumeric(context,
                                it).take(17)
                                checkingError = checkingNumber.length < 17},
                            label = { Text("Checking Account") },
                            isError = checkingError,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = { accountNumberFocusRequester.requestFocus() })
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                }
                item {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextField(
                            modifier = Modifier.width(250.dp).focusRequester(accountNumberFocusRequester),
                            value = accountNumber,
                            onValueChange = { accountNumber = ensureNumeric(context,
                                it).take(17)
                                accountError = accountNumber.length < 17},
                            label = { Text("Account Number") },
                            isError = accountError,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = { routingNumberFocusRequester.requestFocus() })
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                }
                item {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextField(
                            modifier = Modifier.width(250.dp).focusRequester(routingNumberFocusRequester),
                            value = routingNumber,
                            onValueChange = { routingNumber = ensureNumeric(context, it).take(9)
                                routingError = routingNumber.length < 9},
                            label = { Text("Routing Number") },
                            isError = routingError,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = { addressFocusRequester.requestFocus() })
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                }
                item {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextField(
                            modifier = Modifier.width(250.dp).focusRequester(addressFocusRequester),
                            value = address,
                            onValueChange = { address = it },
                            label = { Text("Address") },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = { zipFocusRequester.requestFocus() })
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                }
                item {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextField(
                            modifier = Modifier.width(125.dp).focusRequester(zipFocusRequester),
                            value = zip,
                            onValueChange = { zip = it },
                            label = { Text("zip") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = { stateFocusRequester.requestFocus() })
                        )
                        Spacer(Modifier.width(10.dp))
                        TextField(
                            modifier = Modifier.width(125.dp).focusRequester(stateFocusRequester),
                            value = state,
                            onValueChange = { state = it },
                            label = { Text("State") },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(onNext = { nameFocusRequester.requestFocus() })
                        )
                    }
                    Spacer(Modifier.height(10.dp))
                }
                item {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextField(
                            modifier = Modifier.width(125.dp).focusRequester(nameFocusRequester),
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Name") },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() })
                        )
                    }

                    Spacer(modifier = Modifier.height(80.dp))
                }
                item {
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Button(
                            onClick = {addPaymentMethod(
                                context = context,
                                checking = checkingNumber,
                                account = accountNumber,
                                routingNumber = routingNumber,
                                address = address,
                                state = state,
                                zip = zip,
                                name = name
                                )
                                onBack() },
                            modifier = Modifier
                                .width(300.dp)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFB53A1D)
                            )
                        ) {
                            Text("Add Payment Method")
                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Button(
                            onClick = { onBack() },
                            modifier = Modifier
                                .width(300.dp)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFB53A1D)
                            )
                        ) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }
    }
}
// AI wrote these 3 functions.
fun addPaymentMethod(
    context: Context,
    checking: String,
    account: String,
    routingNumber: String,
    address: String,
    state: String,
    zip: String,
    name: String
) {
    val gson = Gson()
    val file = File(context.filesDir, "payment_methods.json")

    // Load existing list
    val paymentList: MutableList<PaymentMethod> =
        if (file.exists()) {
            val json = file.readText()
            gson.fromJson(json, Array<PaymentMethod>::class.java)?.toMutableList() ?: mutableListOf()
        } else {
            mutableListOf()
        }

    // Add the new entry
    paymentList.add(
        PaymentMethod(
            checking = checking,
            account = account,
            routing = routingNumber,
            address = address,
            state = state,
            zip = zip,
            name = name
        )
    )

    // Save updated list
    file.writeText(gson.toJson(paymentList))

    println("Saved to: ${file.absolutePath}")
}

fun loadPaymentMethods(context: Context): List<PaymentMethod> {
    val file = File(context.filesDir, "payment_methods.json")
    if (!file.exists()) return emptyList()

    val json = file.readText()
    return Gson().fromJson(json, Array<PaymentMethod>::class.java).toList()
}

fun deletePaymentMethod(context: Context, index: Int) {
    val file = File(context.filesDir, "payment_methods.json")
    if (!file.exists()) return

    val gson = Gson()
    val list = gson.fromJson(file.readText(), Array<PaymentMethod>::class.java).toMutableList()

    if (index in list.indices) {
        list.removeAt(index)
    }

    file.writeText(gson.toJson(list))
}
