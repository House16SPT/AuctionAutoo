package com.example.auctionauto.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.auctionauto.UserSession.currentEmail
import com.example.auctionauto.data.AppDatabase
import com.example.auctionauto.data.BidRepo
import com.example.auctionauto.data.ListingRepo
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyListingsScreen(onBack: () -> Unit){
    val context = LocalContext.current

    val database = AppDatabase.getDatabase(context)
    val repo = ListingRepo(database.listingDao())
    val bidRepo = BidRepo(database.bidDao())

    val viewModel: ListingViewModel = viewModel(
        factory = ListingVMFactory(repo,bidRepo)
    )

    LaunchedEffect(Unit) {
        viewModel.loadListings()
    }

    val email = UserSession.currentEmail

    val listings by viewModel.listings.collectAsState()

    val filteredListings = listings.filter { it.author == currentEmail }

    // MyListings implementation
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Image(
                    painter = painterResource(id = R.drawable.mylistingsp),
                    contentDescription = "mylistings",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // take all vertical space
            ) {
                items(filteredListings) { listing ->
                    Box(modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(color = Color.LightGray),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Column() {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                Text(
                                    "${listing.year} ${listing.make} ${listing.model} (${listing.color})",
                                    fontSize = 20.sp
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                horizontalArrangement = Arrangement.Center,
                            ) {
                                val formattedPrice = NumberFormat.getNumberInstance(Locale.US)
                                    .format(listing.price)
                                Text(
                                    "$${formattedPrice}",
                                    fontSize = 20.sp
                                )
                            }
                            Row() {
                                Image(
                                    painter = rememberAsyncImagePainter(listing.image),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(8.dp),
                            ) {
                                Text(
                                    "${listing.description} ",
                                    fontSize = 20.sp
                                )
                            }
                            Row(
                                modifier = Modifier.fillMaxWidth()
                                    .padding(8.dp),
                            ) {
                                Text(
                                    "Time Left: ${listing.duration} days ",
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
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