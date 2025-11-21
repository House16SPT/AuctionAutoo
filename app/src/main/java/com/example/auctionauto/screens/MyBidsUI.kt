package com.example.auctionauto.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.auctionauto.ListingVMFactory
import com.example.auctionauto.ListingViewModel
import com.example.auctionauto.data.AppDatabase
import com.example.auctionauto.data.ListingRepo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBidsScreen(onBack: () -> Unit){
    val context = LocalContext.current

    val database = AppDatabase.getDatabase(context)
    val repo = ListingRepo(database.listingDao())


    val viewModel: ListingViewModel = viewModel(
        factory = ListingVMFactory(repo)
    )

    LaunchedEffect(Unit) {
        viewModel.loadListings()
    }



    // MyListings implementation
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Box{
                        Text("My Bids")}
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
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {


        }
    }
}