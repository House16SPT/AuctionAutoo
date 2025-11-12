package com.example.auctionauto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.unit.sp
import com.example.auctionauto.ui.theme.AuctionAutoTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable


class Dashboard : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuctionAutoTheme {
                DashboardScreen()
            }
        }
    }
}

@Composable
fun DashboardScreen(){
    var showMakeListings by remember { mutableStateOf(false) }
    var showMyListings by remember { mutableStateOf(false) }
    var showMyBids by remember { mutableStateOf(false) }
    var showAccountInfo by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        if (showMakeListings){
            MakeListings(onBack = {showMakeListings = false})
        }
        else if(showMyListings){
            MyListings(onBack = {showMyListings = false})
        }
        else if (showMyBids){
            MyBids(onBack = {showMyBids = false})
        }
        else if (showAccountInfo){
            AccountInfo(onBack = {showAccountInfo = false})
        }
        else{
            DashboardContent(
                modifier = Modifier.padding(innerPadding),
                onMakeListingsClick = {showMakeListings = true},
                onMyListingsClick = {showMyListings = true},
                onMyBidsClick = {showMyBids = true},
                onAccountInfoClick = {showAccountInfo = true}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardContent(
    modifier: Modifier = Modifier,
    onMakeListingsClick: () -> Unit,
    onMyListingsClick: () -> Unit,
    onMyBidsClick: () -> Unit,
    onAccountInfoClick: () -> Unit
){

    var expanded by remember { mutableStateOf(false) }
    var selectedPage by remember { mutableStateOf("Dashboard") }

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
                            ){
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
                                            selectedPage = "Make a listing"
                                            onMakeListingsClick()
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text("My listings") },
                                        onClick = {
                                            expanded = false
                                            selectedPage = "My listings"
                                            onMyListingsClick()
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text("My bids") },
                                        onClick = {
                                            expanded = false
                                            selectedPage = "My bids"
                                            onMyBidsClick()
                                        }
                                    )
                                    DropdownMenuItem(
                                        text = { Text("Make a listing") },
                                        onClick = {
                                            expanded = false
                                            selectedPage = "Make a listing"
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
                            when(selectedPage){
                                "Make a listing" -> MakeListings(onBack = {selectedPage = "Dashboard"} )
                                "My listings" -> MyListings(onBack = {selectedPage = "Dashboard"})
                                "My bids" -> MyBids(onBack = {selectedPage = "Dashboard"})
                                "Account Info" -> AccountInfo(onBack = {selectedPage = "Dashboard"})
                                else -> Text("Welcome to the Dashboard!", fontSize = 24.sp)
                            }
                        }
                    }
                )
            }

@Composable
fun MakeListings(onBack: () -> Unit){

    // MakeListings implementation
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){}

}

@Composable
fun MyListings(onBack: () -> Unit){

    // MyListings implementation
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){
    }

}

@Composable
fun MyBids(onBack: () -> Unit){

    // MyBids implementation
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){}
}

@Composable
fun AccountInfo(onBack: () -> Unit){

    // AccountInfo implementation
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ){}
}







