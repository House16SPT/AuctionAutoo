package com.example.auctionauto.screens

import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.auctionauto.ListingVMFactory
import com.example.auctionauto.ListingViewModel
import com.example.auctionauto.R
import com.example.auctionauto.data.AppDatabase
import com.example.auctionauto.data.ListingRepo
import androidx.compose.foundation.lazy.items
import com.example.auctionauto.data.BidRepo
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onMakeListingClick: () -> Unit,
    onMyListingsClick: () -> Unit,
    onMyBidsClick: () -> Unit,
    onAccountInfoClick: () -> Unit,
    onBack:() -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

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

    val listings by viewModel.listings.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Image(
                        painter = painterResource(id = R.drawable.auctionauto),
                        contentDescription = "AuctionAuto Logo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp)
                            .height(65.dp)
                            .width(275.dp),
                        contentScale = ContentScale.FillBounds
                    )
                },

                actions = {
                    Box {
                        Button(
                            onClick = { expanded = !expanded },
                            modifier = Modifier
                                .width(80.dp)
                                .height(70.dp),
                            shape = RoundedCornerShape(0.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.auctionautosquarebutton),
                                contentDescription = "Menu Button",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.FillBounds
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Make a listing") },
                                onClick = {
                                    expanded = false
                                    onMakeListingClick()
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("My listings") },
                                onClick = {
                                    expanded = false
                                    onMyListingsClick()
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("My bids") },
                                onClick = {
                                    expanded = false
                                    onMyBidsClick()
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Account info") },
                                onClick = {
                                    expanded = false
                                    onAccountInfoClick()
                                }
                            )
                            DropdownMenuItem(
                                    text = { Text("Log out") },
                            onClick = {
                                expanded = false
                                onBack()
                            }
                            )
                        }
                    }
                },
                modifier = Modifier.height(100.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFB53A1D),
                    titleContentColor = Color.White
                )
            )
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(listings) { listing ->
                        Box(modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .background(color = Color.LightGray),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(15.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically // Good for consistency
                                ) {
                                    Text(
                                        "${listing.year} ${listing.make} ${listing.model}",
                                        fontSize = 20.sp
                                    )
                                    val formattedPrice = NumberFormat.getNumberInstance(Locale.US)
                                        .format(listing.price)
                                    Text(
                                        "$${formattedPrice}",
                                        fontSize = 20.sp
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(15.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        "${listing.color} ",
                                        fontSize = 20.sp
                                    )
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Bid +$100:",
                                            fontSize = 20.sp,
                                            modifier = Modifier.padding(end = 4.dp)
                                        )
                                        IconButton(onClick = { viewModel.increasePrice(listing.id)
                                            viewModel.addBid(listing.id)
                                        },
                                            modifier = Modifier.size(40.dp)
                                        ) {
                                            Icon(
                                                modifier = Modifier.size(30.dp),
                                                imageVector = Icons.Filled.ShoppingCart,
                                                contentDescription = "Cart",
                                                tint = Color(0xFFB53A1D)
                                            )
                                        }
                                    }
                                }
                                Row {
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
                                        .padding(15.dp),
                                ) {
                                    Text(
                                        "${listing.description} ",
                                        fontSize = 20.sp
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth()
                                        .padding(15.dp),
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
            }
        }
    )
}