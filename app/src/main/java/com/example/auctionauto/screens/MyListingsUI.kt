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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.auctionauto.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyListingsScreen(onBack: () -> Unit){

    // MyListings implementation
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Box{
                        Text("My Listings")}
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
            ){
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ){

                    Button(
                        onClick = {onBack()},
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