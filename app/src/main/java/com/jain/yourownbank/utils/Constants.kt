package com.jain.yourownbank.utils

import com.jain.yourownbank.models.auth.LoginDataModel
import com.jain.yourownbank.models.auth.User
import com.jain.yourownbank.models.service.ServiceModel
import com.jain.yourownbank.models.setting.SettingModel
import com.jain.yourownbank.models.transaction.UserBankDetails
import com.jain.yourownbank.screensRoutes.ScreensRoutes

class Constants {
    companion object {
        val SERVICE_LIST = listOf(
            ServiceModel(id = "0", title = "Home", route = ScreensRoutes.HomeScreen.route),
            ServiceModel(id = "2", title = "Your QR", route = ScreensRoutes.YourYOBQRCodeScreen.route),
            ServiceModel(id = "3", title = "Send Money", route = ScreensRoutes.SendMoneyScreen.route),
            ServiceModel(id = "4", title = "Transaction History", route = ScreensRoutes.TransactionHistoryListScreen.route)
        )
        val SETTING_LIST = listOf(
            SettingModel(id = "3", title = "About", route = ScreensRoutes.AboutYOBScreen.route),
            SettingModel(id = "1", title = "Delete Account", route = ScreensRoutes.DeleteUserRequestScreen.route),
            SettingModel(id = "2", title = "Contact us", route = ScreensRoutes.TransactionHistoryListScreen.route),
            SettingModel(id = "0", title = "Logout", route = ScreensRoutes.LogoutScreen.route)
        )
        const val BASE_PATH_URL = "https://yourownbankapi-production.up.railway.app"
        const val CONTENT_TYPE = "application/json"
        var USER = User()
        var USER_COMPLETE_BANK_DETAILS = UserBankDetails()
        var TOKEN = ""
        var USER_ID = ""
    }
}