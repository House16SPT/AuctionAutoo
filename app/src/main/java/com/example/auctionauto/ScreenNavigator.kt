package com.example.auctionauto

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.auctionauto.data.UserRepository
import com.example.auctionauto.screens.*

// manages screens!
@Composable
fun ScreenNavigator() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val userRepo = UserRepository(context) // one instance per composition

    // NavHost displays the currently selected screen (Composable function)
    NavHost(
        navController = navController,
        startDestination = "LOGIN_SCREEN"
    ) {

        composable("LOGIN_SCREEN") {
            LoginScreen(
                onRegisterClick = {
                    navController.navigate("REGISTER_SCREEN")
                },
                onLoginClick = {
                    // on successful login, go to dashboard
                    navController.navigate("DASHBOARD_SCREEN")
                },
                userRepo = userRepo
            )
        }

        composable("REGISTER_SCREEN") {
            RegisterScreen(
                onBack = {
                    navController.popBackStack()
                },
                userRepo = userRepo
            )
        }

        composable("DASHBOARD_SCREEN") {
            DashboardScreen(
                onMakeListingClick = {
                    navController.navigate("MAKE_LISTINGS_SCREEN")
                },
                onMyListingsClick = {
                    navController.navigate("MY_LISTINGS_SCREEN")
                },
                onMyBidsClick = {
                    navController.navigate("MY_BIDS_SCREEN")
                },
                onAccountInfoClick = {
                    navController.navigate("ACCOUNT_INFO_SCREEN")
                },
                onBack = {
                    navController.popBackStack()  // go back to previous screen.
                }
            )
        }

        composable("MAKE_LISTINGS_SCREEN") {
            MakeListingScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("MY_LISTINGS_SCREEN") {
            MyListingsScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("MY_BIDS_SCREEN") {
            MyBidsScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("ACCOUNT_INFO_SCREEN") {
            AccountInfoScreen(
                onPaymentInfoClick = {
                    navController.navigate("PAYMENT_INFO_SCREEN")
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable("PAYMENT_INFO_SCREEN") {
            PaymentInfoScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

    }
}
