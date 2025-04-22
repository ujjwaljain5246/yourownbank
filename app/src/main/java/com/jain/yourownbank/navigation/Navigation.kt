package com.jain.yourownbank.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jain.yourownbank.screens.auth.SignIn
import com.jain.yourownbank.screens.auth.SignUp
import com.jain.yourownbank.screens.home.Home
import com.jain.yourownbank.screens.service.ServiceScreen
import com.jain.yourownbank.screens.transaction.SendMoney
import com.jain.yourownbank.screens.transaction.TransactionDetails
import com.jain.yourownbank.screens.transaction.TransactionHistory
import com.jain.yourownbank.models.transaction.TransactionHistory
import com.jain.yourownbank.screens.auth.DeleteUserScreen
import com.jain.yourownbank.screens.auth.LogoutScreen
import com.jain.yourownbank.screens.setting.AboutYobScreen
import com.jain.yourownbank.screens.setting.SettingScreen
import com.jain.yourownbank.screens.transaction.AvailableBalanceScreen
import com.jain.yourownbank.screens.transaction.ScanningYOBQRCodeScreen
import com.jain.yourownbank.screens.transaction.TransactionSuccessScreen
import com.jain.yourownbank.screens.transaction.YOBQRCodeScreen
import com.jain.yourownbank.screensRoutes.ScreensRoutes

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreensRoutes.SignInScreen.route) {
        composable(route = ScreensRoutes.SignInScreen.route) {
            SignIn(navController)
        }
        composable(route = ScreensRoutes.SignUpScreen.route) {
            SignUp(navController = navController)
        }
        composable(route = ScreensRoutes.HomeScreen.route) {
            Home(navController)
        }
        composable(route = ScreensRoutes.ServiceScreen.route) {
            ServiceScreen(navController)
        }
        composable(route = ScreensRoutes.TransactionHistoryListScreen.route) {
            TransactionHistory(navController)
        }
        composable(route = ScreensRoutes.TransactionDetailsScreen.route) {navBackStackEntry ->
            TransactionDetails(navController = navController)
        }
        composable(route = ScreensRoutes.SendMoneyScreen.route) {
            SendMoney(navController = navController)
        }
        composable(route = ScreensRoutes.TransactionSuccessScreen.route) {
            TransactionSuccessScreen(navController = navController)
        }
        composable(route = ScreensRoutes.SettingScreen.route) {
            SettingScreen(navController)
        }
        composable(route = ScreensRoutes.LogoutScreen.route) {
            LogoutScreen(navController)
        }
        composable(route = ScreensRoutes.DeleteUserRequestScreen.route) {
            DeleteUserScreen(navController = navController)
        }
        composable(route = ScreensRoutes.YourYOBQRCodeScreen.route) {
            YOBQRCodeScreen()
        }
        composable(route = ScreensRoutes.ScanningYOBQRCodeScreen.route) {
            ScanningYOBQRCodeScreen(
                navController = navController
            )
        }
        composable(route = ScreensRoutes.AvailableBalanceScreen.route) {
            AvailableBalanceScreen(navController = navController)
        }
        composable(route = ScreensRoutes.AboutYOBScreen.route) {
            AboutYobScreen(navController = navController)
        }
    }
}