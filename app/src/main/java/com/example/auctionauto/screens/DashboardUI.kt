package com.example.auctionauto.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.auctionauto.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onMakeListingClick: () -> Unit,
    onMyListingsClick: () -> Unit,
    onMyBidsClick: () -> Unit,
    onAccountInfoClick: () -> Unit,
    onBack: () -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box {
                        Image(
                            painter = painterResource(id = R.drawable.auctionauto),
                            contentDescription = "AuctionAuto Logo",
                            modifier = Modifier
                                .height(65.dp)
                                .width(275.dp),
                            contentScale = ContentScale.FillBounds
                        )
                        Box(
                            modifier = Modifier
                                .offset(x = 300.dp, y = 0.dp)
                        ) {
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
                                    contentDescription = "Square Button",
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
                            }
                        }
                    }
                },
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
                Text("Welcome to the Dashboard!", fontSize = 24.sp)
            }
        }
    )
}