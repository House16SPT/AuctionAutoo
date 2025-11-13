package com.example.auctionauto

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.auctionauto.screens.*

// manages screens!
@Composable
fun ScreenNavigator() {
    val navController = rememberNavController()

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
                    navController.navigate("DASHBOARD_SCREEN")
                }
            )
        }

        composable("REGISTER_SCREEN") {
            RegisterScreen(
                onBack = {
                    navController.popBackStack()
                }
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
                    navController.popBackStack()
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
                onBack = {
                    navController.popBackStack()
                }
            )
        }

    }
}