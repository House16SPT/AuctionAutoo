package com.example.auctionauto.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.auctionauto.ListingVMFactory
import com.example.auctionauto.ListingViewModel
import com.example.auctionauto.R
import com.example.auctionauto.UserSession
import com.example.auctionauto.data.AppDatabase
import com.example.auctionauto.data.BidRepo
import com.example.auctionauto.data.Listing
import com.example.auctionauto.data.ListingRepo
import com.example.auctionauto.ensureNumeric

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
    var imageUri by remember { mutableStateOf<String?>(null) }
    val email = UserSession.currentEmail

    // these vars to change focused text field on enter
    val priceInputFocusRequester = remember { FocusRequester() }
    val durationInputFocusRequester = remember { FocusRequester() }
    val makeFocusRequester = remember { FocusRequester() }
    val modelFocusRequester = remember { FocusRequester() }
    val colorFocusRequester = remember { FocusRequester() }
    val yearFocusRequester = remember { FocusRequester() }
    val descriptionFocusRequester = remember { FocusRequester() }

    val context = LocalContext.current // for displaying error messages (non numeric input, etc.)

    val database = AppDatabase.getDatabase(context)
    val repo = ListingRepo(database.listingDao())
    val bidRepo = BidRepo(database.bidDao())

    val viewModel: ListingViewModel = viewModel(
        factory = ListingVMFactory(repo,bidRepo)
    )

    //Launcher written by ChatGPT 5.1 Instant
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            context.contentResolver.takePersistableUriPermission(
                uri,   // ← use uri here, not it
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            imageUri = uri.toString()
        }
    }

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
            contentAlignment = Alignment.TopCenter
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            )
            {
                item {
                    Box(modifier = Modifier) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Button(
                                    onClick = { launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) },
                                    modifier = Modifier.padding(10.dp)
                                ) {
                                    Text("Choose Picture")
                                }
                            }
                            Row(
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                TextField(
                                    modifier = Modifier.width(125.dp).focusRequester(priceInputFocusRequester),
                                    value = priceInput,
                                    onValueChange = { priceInput = ensureNumeric(context, it) },
                                    label = { Text("Starting price") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                                    keyboardActions = KeyboardActions(onNext = { durationInputFocusRequester.requestFocus() })
                                )
                                Spacer(Modifier.width(10.dp))
                                TextField(
                                    modifier = Modifier.width(125.dp).focusRequester(durationInputFocusRequester),
                                    value = durationInput,
                                    onValueChange = { durationInput = ensureNumeric(context, it) },
                                    label = { Text("Duration (days)") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                                    keyboardActions = KeyboardActions(onNext = { makeFocusRequester.requestFocus() })
                                )
                            }
                            Spacer(Modifier.height(10.dp))
                            Row(
                                verticalAlignment = Alignment.Top,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                TextField(
                                    modifier = Modifier.width(125.dp).focusRequester(makeFocusRequester),
                                    value = make,
                                    onValueChange = { make = it },
                                    label = { Text("Make") },
                                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                    keyboardActions = KeyboardActions(onNext = { modelFocusRequester.requestFocus() })
                                )
                                Spacer(Modifier.width(10.dp))
                                TextField(
                                    modifier = Modifier.width(125.dp).focusRequester(modelFocusRequester),
                                    value = model,
                                    onValueChange = { model = it },
                                    label = { Text("Model") },
                                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                    keyboardActions = KeyboardActions(onNext = { colorFocusRequester.requestFocus() })
                                )
                            }
                            Spacer(Modifier.height(10.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                TextField(
                                    modifier = Modifier.width(125.dp).focusRequester(colorFocusRequester),
                                    value = color,
                                    onValueChange = { color = it },
                                    label = { Text("Color") },
                                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                    keyboardActions = KeyboardActions(onNext = { yearFocusRequester.requestFocus() })
                                )
                                Spacer(Modifier.width(10.dp))
                                TextField(
                                    modifier = Modifier.width(125.dp).focusRequester(yearFocusRequester),
                                    value = year,
                                    onValueChange = { year = ensureNumeric(context, it) },
                                    label = { Text("Year") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Next),
                                    keyboardActions = KeyboardActions(onNext = { descriptionFocusRequester.requestFocus() })
                                )
                            }

                            Spacer(Modifier.height(20.dp))

                            Row(
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.Center
                            )
                            {
                                TextField(
                                    modifier = Modifier
                                        .width(300.dp)
                                        .height(200.dp).focusRequester(descriptionFocusRequester),
                                    value = description,
                                    onValueChange = { description = it },
                                    label = { Text("Description") }
                                )
                            }

                            Spacer(Modifier.height(20.dp))

                            Button(
                                onClick = {
                                    if (make.isEmpty() || model.isEmpty() || year.isEmpty() || color.isEmpty() || priceInput.isEmpty() || description.isEmpty() || durationInput.isEmpty()) {
                                        Toast.makeText(context, "Fill all fields", Toast.LENGTH_SHORT).show()
                                        return@Button
                                    }

                                    val newListing = Listing(
                                        make = make,
                                        model = model,
                                        year = year,
                                        color = color,
                                        price = priceInput.toIntOrNull() ?: 0,
                                        description = description,
                                        author = email,
                                        duration = durationInput.toIntOrNull() ?: 0,
                                        image = imageUri ?: ""
                                    )
                                    viewModel.addListing(newListing)
                                    onBack()
                                },
                                modifier = Modifier
                                    .width(300.dp)
                                    .height(50.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFB53A1D)
                                )
                            ) {
                                Text("Post Listing")
                            }

                            Spacer(Modifier.height(20.dp))

                            Button(
                                onClick = {
                                    onBack()
                                },
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
}
