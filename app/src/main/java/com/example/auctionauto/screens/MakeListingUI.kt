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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.lifecycle.viewmodel.compose.viewModel
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
import com.example.auctionauto.ListingVMFactory
import com.example.auctionauto.ListingViewModel
import com.example.auctionauto.R
import com.example.auctionauto.data.AppDatabase
import com.example.auctionauto.data.ListingRepo
import com.example.auctionauto.ensureNumeric
import com.example.auctionauto.data.Listing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MakeListingScreen(onBack: () -> Unit) {
    var priceInput by remember { mutableStateOf("") } // (starting price)
    var durationInput by remember { mutableStateOf("") } // duration of auction
    var make by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var year by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    val context = LocalContext.current // for displaying error messages (non numeric input, etc.)

    val database = AppDatabase.getDatabase(context)
    val repo = ListingRepo(database.listingDao())

    val viewModel: ListingViewModel = viewModel(
        factory = ListingVMFactory(repo)
    )



    // MakeListing implementation
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.makealisting),
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

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(
                        modifier = Modifier.width(125.dp),
                        value = priceInput,
                        onValueChange = { priceInput = ensureNumeric(context, it) },
                        label = { Text("Starting price") },
                    )
                    Spacer(Modifier.width(10.dp))
                    TextField(
                        modifier = Modifier.width(125.dp),
                        value = durationInput,
                        onValueChange = { durationInput = ensureNumeric(context, it) },
                        label = { Text("Duration (days)") },
                    )
                }
                Spacer(Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(
                        modifier = Modifier.width(125.dp),
                        value = make,
                        onValueChange = { make = it },
                        label = { Text("Make") },
                    )
                    Spacer(Modifier.width(10.dp))
                    TextField(
                        modifier = Modifier.width(125.dp),
                        value = model,
                        onValueChange = { model = it },
                        label = { Text("Model") },
                    )
                }
                Spacer(Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(
                        modifier = Modifier.width(125.dp),
                        value = color,
                        onValueChange = { color = it },
                        label = { Text("Color") },
                    )
                    Spacer(Modifier.width(10.dp))
                    TextField(
                        modifier = Modifier.width(125.dp),
                        value = year,
                        onValueChange = { year = ensureNumeric(context, it) },
                        label = { Text("Year") },
                    )
                }
                Spacer(Modifier.height(60.dp))
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                )
                {
                    TextField(
                        modifier = Modifier
                            .width(300.dp)
                            .height(200.dp),
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Description") },
                    )
                }
            }
            Button(
                onClick = {
                    val newListing = Listing(
                        make = make,
                        model = model,
                        year = year,
                        color = color,
                        price = priceInput.toIntOrNull() ?: 0,
                        description = description,
                        author = "Uknown",
                        duration = durationInput.toIntOrNull() ?: 0
                    )
                    viewModel.addListing(newListing)
                    onBack()
                },
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
                    .align(Alignment.BottomCenter),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB53A1D)
                )
            ) {
                Text("Post Listing")
            }
        }
    }
}
