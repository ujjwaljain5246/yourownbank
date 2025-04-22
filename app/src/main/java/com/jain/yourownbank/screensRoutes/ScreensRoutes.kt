package com.jain.yourownbank.screensRoutes

sealed class ScreensRoutes (val route: String) {
    object SignInScreen: ScreensRoutes(route = "signInScreen")
    object SignUpScreen: ScreensRoutes(route = "signUpScreen")
    object HomeScreen: ScreensRoutes(route = "homeScreen")
    object ServiceScreen: ScreensRoutes(route = "serviceScreen")
    object SendMoneyScreen: ScreensRoutes(route = "sendMoneyScreen")
    object TransactionHistoryListScreen: ScreensRoutes(route = "transactionHistoryListScreen")
    object TransactionDetailsScreen: ScreensRoutes(route = "transactionDetailsScreen")
    object TransactionSuccessScreen: ScreensRoutes(route = "transactionSuccessScreen")
    object SettingScreen: ScreensRoutes(route = "settingScreen")
    object LogoutScreen: ScreensRoutes(route = "logoutScreen")
    object DeleteUserRequestScreen: ScreensRoutes(route = "deleteUserScreen")
    object YourYOBQRCodeScreen: ScreensRoutes(route = "yourYobQRScreen")
    object ScanningYOBQRCodeScreen: ScreensRoutes(route = "scanningYouQRCodeScreen")
    object AvailableBalanceScreen: ScreensRoutes(route = "availableScreenScreen")
    object AboutYOBScreen: ScreensRoutes(route = "aboutYOBScreen")
}