package com.example.auctionauto.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.auctionauto.ListingVMFactory
import com.example.auctionauto.ListingViewModel
import com.example.auctionauto.R
import com.example.auctionauto.UserSession
import com.example.auctionauto.data.*
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBidsScreen(onBack: () -> Unit) {

    val context = LocalContext.current

    val database = AppDatabase.getDatabase(context)
    val listingRepo = ListingRepo(database.listingDao())
    val bidRepo = BidRepo(database.bidDao())

    val viewModel: ListingViewModel = viewModel(
        factory = ListingVMFactory(listingRepo,bidRepo)
    )

    // load listings
    LaunchedEffect(Unit) {
        viewModel.loadListings()
    }

    val listings by viewModel.listings.collectAsState()
    var userBids by remember { mutableStateOf<List<Bid>>(emptyList()) }

    LaunchedEffect(Unit) {
        val email = UserSession.currentEmail ?: ""
        userBids = bidRepo.getUserBids(email)
    }

    val bidListingIds = userBids.map { it.listingId }
    val filteredListings = listings.filter { it.id in bidListingIds }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.mybidsp),
                        contentDescription = "mybids",
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
                .padding(top = 20.dp),
            contentAlignment = Alignment.TopCenter
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(filteredListings) { listing ->
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .background(Color.LightGray)
                    ) {
                        Column {

                            // title row
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "${listing.year} ${listing.make} ${listing.model}",
                                    fontSize = 20.sp
                                )
                                val formattedPrice =
                                    NumberFormat.getNumberInstance(Locale.US).format(listing.price)
                                Text(
                                    "$$formattedPrice",
                                    fontSize = 20.sp
                                )
                            }

                            // color row
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp),
                            ) {
                                Text(
                                    "${listing.color}",
                                    fontSize = 20.sp
                                )
                            }

                            // image row
                            Image(
                                painter = rememberAsyncImagePainter(listing.image),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentScale = ContentScale.Crop
                            )

                            // description
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp)
                            ) {
                                Text(
                                    listing.description,
                                    fontSize = 20.sp
                                )
                            }

                            // time left
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp)
                            ) {
                                Text(
                                    "Time Left: ${listing.duration} days",
                                    fontSize = 20.sp
                                )
                            }
                        }
                        if (listing.duration == 0) { //checks to see if time = 0 and creates overlay for listing with pay now button
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .matchParentSize()
                                    .background(Color(0xAA000000)),
                                contentAlignment = Alignment.Center
                            ) {
                                Button(
                                    onClick = {
                                        val paymentMethods = loadPaymentMethods(context)

                                        if (paymentMethods.isEmpty()) {
                                            Toast.makeText(context, "Add a payment method first!", Toast.LENGTH_SHORT).show()
                                            return@Button
                                        }
                                        viewModel.deleteListing(listing)
                                        Toast.makeText(context, "Payment successful!", Toast.LENGTH_SHORT).show()
                                    },
                                    modifier = Modifier
                                        .width(200.dp)
                                        .height(60.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFB53A1D)
                                    )
                                ) {
                                    Text("Pay Now", fontSize = 22.sp, color = Color.White)
                                }
                            }
                        }
                    }

                }
            }

            // BACK BUTTON
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
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
